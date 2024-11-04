package com.example.nutrition.domain.service;

import com.azure.storage.blob.*;
import com.example.nutrition.domain.entity.Nutrition;
import com.example.nutrition.domain.entity.User;
import com.example.nutrition.domain.entity.UserNutrition;
import com.example.nutrition.domain.dto.JoinRequest;
import com.example.nutrition.domain.dto.LoginRequest;
import com.example.nutrition.domain.repository.NutritionRepository;
import com.example.nutrition.domain.repository.UserNutritionRepository;
import com.example.nutrition.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.imgscalr.Scalr;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import org.mindrot.jbcrypt.BCrypt;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserNutritionRepository userNutritionRepository;
    private final NutritionRepository nutritionRepository;

    @Value("${custom.api.azure_connection}")
    private String connectionString;

    @Value("${custom.api.ocr_url}")
    private String apiURL;

    @Value("${custom.api.ocr_key}")
    private String secretKey;

    public boolean checkLoginIdDuplicate(String loginId) {
        return userRepository.existsByLoginId(loginId);
    }

    public String join(JoinRequest req) {
        if (!checkLoginIdDuplicate(req.getLoginId())) {
            userRepository.save(req.toEntity());
            return req.getLoginId();
        }
        else {
            return null;
        }
    }

    public String login(LoginRequest req) {
        Optional<User> optionalUser = userRepository.findByLoginId(req.getLoginId());

        if(optionalUser.isEmpty()) {
            return null;
        }

        User user = optionalUser.get();

        boolean isMatch = BCrypt.checkpw(req.getPassword(), user.getPassword());

        if(!isMatch) {
            return null;
        }

        return user.getLoginId();
    }

    public boolean uploadImage(MultipartFile file, String id) throws IOException, InterruptedException {
        String containerName = "nutrition";
        UUID uuid = UUID.randomUUID();
        String blobFileName = id + "#" + uuid;
        String file_url;

        try {
            BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                    .connectionString(connectionString)
                    .buildClient();

            BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);

            if (!containerClient.exists()) {
                containerClient.create();
            }

            BlobClient blobClient = containerClient.getBlobClient(blobFileName);

            MultipartFile resizedFile = resizeImage(file, 850, 850);
            InputStream fileInputStream = resizedFile.getInputStream();

            blobClient.upload(fileInputStream);
            file_url = blobClient.getBlobUrl();
            System.out.println(file_url);

            StringBuffer result = run_clova(file_url, apiURL, secretKey);
            ArrayList<Float> array = preprocessing(result);
            UserNutrition temp = UserNutrition.builder()
                    .userId(id)
                    .kcal(array.get(0))
                    .carb(array.get(1))
                    .sugar(array.get(2))
                    .Fat(array.get(3))
                    .protein(array.get(4))
                    .imageurl(file_url)
                    .date(LocalDate.now())
                    .build();
            this.userNutritionRepository.save(temp);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public StringBuffer run_clova(String file_url, String apiURL, String secretKey) {

        try {
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setUseCaches(false);
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            con.setRequestProperty("X-OCR-SECRET", secretKey);

            JSONObject json = new JSONObject();
            json.put("version", "V2");
            json.put("requestId", UUID.randomUUID().toString());
            json.put("timestamp", System.currentTimeMillis());
            JSONObject image = new JSONObject();
            image.put("format", "jpg");
            image.put("url", file_url); // image should be public, otherwise, should use data
                // FileInputStream inputStream = new FileInputStream("YOUR_IMAGE_FILE");
                // byte[] buffer = new byte[inputStream.available()];
                // inputStream.read(buffer);
                // inputStream.close();
                // image.put("data", buffer);
            image.put("name", "demo");
            JSONArray images = new JSONArray();
            images.put(image);
            json.put("images", images);
            String postParams = json.toString();

            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(postParams);
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == 200) {
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            return response;
        } catch (Exception e) {
            System.out.println(e);
            return null;
            }
        }

        public ArrayList<Float> preprocessing(StringBuffer result) {

            List<String> nutrition_list = List.of("kcal", "탄수화물", "당류", "지방", "단백질");
            ArrayList<String> list = new ArrayList<>();
            ArrayList<Float> nutrition_array = new ArrayList<>();

            String nutrition_result = result.toString();
            JSONObject jsonObject = new JSONObject(nutrition_result);
            JSONArray imagesArray = jsonObject.getJSONArray("images");
            JSONObject firstImage = imagesArray.getJSONObject(0);
            JSONArray fieldsArray = firstImage.getJSONArray("fields");

            for (String nutirition : nutrition_list) {
                for (int i = 0; i < fieldsArray.length(); i++) {
                    System.out.println(i);
                    JSONObject field = fieldsArray.getJSONObject(i);
                    String inferText = field.getString("inferText").trim();

                    if(inferText.contains(nutirition)) {
                        // 특정 영양성분으로 끝날때
                        if(inferText.endsWith(nutirition)) {
                            if (nutirition.equals("kcal")) {
                                JSONObject temp_field = fieldsArray.getJSONObject(i - 1);
                                String temp = temp_field.getString("inferText");
                                list.add(temp);
                                break;
                            } else {
                                JSONObject temp_field = fieldsArray.getJSONObject(i + 1);
                                String temp = temp_field.getString("inferText");
                                list.add(temp);
                                break;
                            }
                        }
                        if(inferText.contains("mg")){
                            inferText = inferText.replace("mg", "");
                        }
                        // nutrition 보다 클때
                        if(inferText.length() > nutirition.length()){
                            int start_index_1 = inferText.indexOf(nutirition);
                            // g 앞의 문자열을 추출
                            if (start_index_1 != -1){
                                int g_index = inferText.indexOf("g");
                                if(inferText.contains("g") && g_index > start_index_1) {
                                    int end_index = inferText.indexOf("g");
                                    System.out.println(inferText);
                                    String temp = inferText.substring(start_index_1 + nutirition.length(), end_index);
                                    list.add(temp);
                                    break;
                                }
                                // g 없을때
                                else{
                                    String temp = inferText.substring(start_index_1 + nutirition.length());
                                    list.add(inferText);
                                    break;
                                }
                            }
                        }
                        else{
                            if (nutirition.equals("kcal")) {
                                JSONObject temp_field = fieldsArray.getJSONObject(i - 1);
                                String temp = temp_field.getString("inferText");
                                list.add(temp);
                                break;
                            } else {
                                JSONObject temp_field = fieldsArray.getJSONObject(i + 1);
                                String temp = temp_field.getString("inferText");
                                list.add(temp);
                                break;
                            }
                        }
                    }

                    double inferConfidence = field.getDouble("inferConfidence");

                    System.out.println("InferText: " + inferText.trim());
                    System.out.println("InferConfidence: " + inferConfidence);
                    System.out.println();
                        }
                    }
                System.out.println(list);

                for(String nutrition : list) {
                    if(nutrition.contains("당")) {
                        int start_index = nutrition.indexOf("당");
                        String temp = nutrition.substring(start_index+1);
                        nutrition_array.add(Float.valueOf(temp));
                    } else if(nutrition.contains("g")){
                        int end_index = nutrition.indexOf("g");
                        String temp = nutrition.substring(0, end_index);
                        nutrition_array.add(Float.valueOf(temp));
                    } else if (nutrition.contains("/")) {
                        String temp = nutrition.replace("/", "");
                        nutrition_array.add(Float.valueOf(temp));
                    } else if (nutrition.contains("%")) {
                        String temp = nutrition.replace("%", "");
                        nutrition_array.add(Float.valueOf(temp));
                    }
                    else{
                        nutrition = nutrition.replaceAll("[^0-9]", "");
                        nutrition_array.add(Float.valueOf(nutrition));
                    }
                }
                System.out.println(nutrition_array);
                return nutrition_array;
                }

        public ArrayList<ArrayList> getNutrition(String id, String nutrition){
            ArrayList<ArrayList> result = new ArrayList<>();
            ArrayList<LocalDate> nutrition_key = new ArrayList<>();
            ArrayList<Float> nutrition_value = new ArrayList<>();
            for(int j=4; j>=0; j--){
                List<UserNutrition> users = this.userNutritionRepository.findByUserIdAndDate(id, LocalDate.now().minusDays(j));

                if (users.isEmpty()){
                    nutrition_key.add(LocalDate.now().minusDays(j));
                    nutrition_value.add((float) 0);
                    continue;
                }

                switch (nutrition) {
                    case "kcal" -> {
                        Float kcal = (float) 0;
                        for (UserNutrition userNutrition : users) {
                            kcal += userNutrition.getKcal();
                        }
                        nutrition_key.add(LocalDate.now().minusDays(j));
                        nutrition_value.add(kcal);
                    }
                    case "carb" -> {
                        Float carb = (float) 0;
                        for (UserNutrition userNutrition : users) {
                            carb += userNutrition.getCarb();
                        }
                        nutrition_key.add(LocalDate.now().minusDays(j));
                        nutrition_value.add(carb);
                    }
                    case "sugar" -> {
                        Float sugar = (float) 0;
                        for (UserNutrition userNutrition : users) {
                            sugar += userNutrition.getSugar();
                        }
                        nutrition_key.add(LocalDate.now().minusDays(j));
                        nutrition_value.add(sugar);
                    }
                    case "fat" -> {
                        Float fat = (float) 0;
                        for (UserNutrition userNutrition : users) {
                            fat += userNutrition.getFat();
                        }
                        nutrition_key.add(LocalDate.now().minusDays(j));
                        nutrition_value.add(fat);
                    }
                    case "protein" -> {
                        Float protein = (float) 0;
                        for (UserNutrition userNutrition : users) {
                            protein += userNutrition.getProtein();
                        }
                        nutrition_key.add(LocalDate.now().minusDays(j));
                        nutrition_value.add(protein);
                    }
                    default -> {
                        return null;
                    }
                }
            }
            result.add(nutrition_key);
            result.add(nutrition_value);
            System.out.println(result);
            return result;
        }

    public ArrayList<ArrayList> getNutritionDaily(String id){
        ArrayList<ArrayList> result = new ArrayList<>();
        ArrayList<Float> nutrition_value = new ArrayList<>();
        ArrayList<String> nutrition_list = new ArrayList<>(List.of("kcal", "carb", "sugar", "fat", "protein"));
        Optional<User> user = this.userRepository.findByLoginId(id);
        List<UserNutrition> nutritions = this.userNutritionRepository.findByUserIdAndDate(id, LocalDate.now());


        if (nutritions.isEmpty()){
            ArrayList<Float> zero_list = new ArrayList<>(List.of(0F, 0F, 0F, 0F, 0F));
            result.add(zero_list);

            if(user.isPresent()){
                ArrayList<Float> temp_list = new ArrayList<>();
                User temp = user.get();
                if(temp.getGender().equals("male")){
                    temp_list.add(1650F);
                }
                else{
                    temp_list.add(1350F);
                }
                temp_list.add(100F);
                temp_list.add(65F);
                temp_list.add((float) (0.8 * temp.getWeight()));
                temp_list.add((float) temp.getWeight());
                temp_list.add(65F);
                result.add(temp_list);
            }
            return result;
        }

        for(String nutrition : nutrition_list){
            switch (nutrition) {
                case "kcal" -> {
                    Float kcal = (float) 0;
                    for (UserNutrition userNutrition : nutritions) {
                        kcal += userNutrition.getKcal();
                    }
                    nutrition_value.add(kcal);
                }
                case "carb" -> {
                    Float carb = (float) 0;
                    for (UserNutrition userNutrition : nutritions) {
                        carb += userNutrition.getCarb();
                    }
                    nutrition_value.add(carb);
                }
                case "sugar" -> {
                    Float sugar = (float) 0;
                    for (UserNutrition userNutrition : nutritions) {
                        sugar += userNutrition.getSugar();
                    }
                    nutrition_value.add(sugar);
                }
                case "fat" -> {
                    Float fat = (float) 0;
                    for (UserNutrition userNutrition : nutritions) {
                        fat += userNutrition.getFat();
                    }
                    nutrition_value.add(fat);
                }
                case "protein" -> {
                    Float protein = (float) 0;
                    for (UserNutrition userNutrition : nutritions) {
                        protein += userNutrition.getProtein();
                    }
                    nutrition_value.add(protein);
                }
            }
        }

        System.out.println(nutrition_value);
        result.add(nutrition_value);

        if(user.isPresent()){
            ArrayList<Float> temp_list = new ArrayList<>();
            User temp = user.get();
            if(temp.getGender().equals("male")){
                temp_list.add(1650F);
            }
            else{
                temp_list.add(1350F);
            }
            temp_list.add(100F);
            temp_list.add(65F);
            temp_list.add((float) (0.8 * temp.getWeight()));
            temp_list.add((float) temp.getWeight());
            temp_list.add(65F);
            result.add(temp_list);
        }

        return result;
    }

        public ArrayList<ArrayList> getNutritionAll(String id) {
            ArrayList<ArrayList> result = new ArrayList<>();
            ArrayList<LocalDate> temp_date = new ArrayList<>();
            ArrayList<Float> temp_kcal = new ArrayList<>();
            ArrayList<Float> temp_carb = new ArrayList<>();
            ArrayList<Float> temp_sugar = new ArrayList<>();
            ArrayList<Float> temp_fat = new ArrayList<>();
            ArrayList<Float> temp_protein = new ArrayList<>();

            for (int j = 4; j >= 0; j--) {
                List<UserNutrition> users = this.userNutritionRepository.findByUserIdAndDate(id, LocalDate.now().minusDays(j));

                if (users.isEmpty()) {
                    temp_date.add(LocalDate.now().minusDays(j));
                    temp_kcal.add((float) 0);
                    temp_carb.add((float) 0);
                    temp_sugar.add((float) 0);
                    temp_fat.add((float) 0);
                    temp_protein.add((float) 0);
                }

                else {
                    temp_date.add(LocalDate.now().minusDays(j));
                    float kcal = (float) 0;
                    float carb = (float) 0;
                    float sugar = (float) 0;
                    float fat = (float) 0;
                    float protein = (float) 0;
                    for (UserNutrition userNutrition : users) {
                        kcal += userNutrition.getKcal();
                        carb += userNutrition.getCarb();
                        sugar += userNutrition.getSugar();
                        fat += userNutrition.getFat();
                        protein += userNutrition.getProtein();
                    }

                    temp_kcal.add(kcal);
                    temp_carb.add(carb);
                    temp_sugar.add(sugar);
                    temp_fat.add(fat);
                    temp_protein.add(protein);
                }
            }
            result.add(temp_date);
            result.add(temp_kcal);
            result.add(temp_carb);
            result.add(temp_sugar);
            result.add(temp_fat);
            result.add(temp_protein);
            System.out.println(result);
            return result;
        }

    MultipartFile resizeImage(MultipartFile file, int width, int height) throws Exception {
        // resize에 들어가는 속성을 변경해서 여러 모드로 변경해줄수있다
        BufferedImage originalImage = ImageIO.read(file.getInputStream());

        // Scalr 라이브러리를 이용해 이미지 리사이징
        BufferedImage resizedImage = Scalr.resize(originalImage, Scalr.Method.QUALITY, width, height);

        // BufferedImage를 byte[]로 변환
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(resizedImage, "jpg", baos);
        byte[] imageBytes = baos.toByteArray();

        // byte[]를 MultipartFile로 변환하여 반환
        return new MockMultipartFile(
                file.getName(),                 // 파일 필드 이름
                file.getOriginalFilename(),      // 원본 파일 이름
                file.getContentType(),           // 파일 Content-Type
                new ByteArrayInputStream(imageBytes) // 파일 데이터
        );
    }

    public String getRecommend(String id) {
        List<UserNutrition> nutritions = this.userNutritionRepository.findByUserIdAndDate(id, LocalDate.now());
        if(nutritions.isEmpty()){
            Optional<Nutrition> recommend = this.nutritionRepository.findRandomRecommend();
            return recommend.get().getName();
        }
        return null;
    }
}


