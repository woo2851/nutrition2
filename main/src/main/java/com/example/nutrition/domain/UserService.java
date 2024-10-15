package com.example.nutrition.domain;

import com.azure.storage.blob.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.*;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;
import java.util.Optional;
import java.util.ArrayList;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Value("${custom.api.key}")
    private String connectionString;

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

    public void uploadImage(MultipartFile file, String id) throws IOException, InterruptedException {

        String containerName = "nutrition";
        UUID uuid = UUID.randomUUID();
        String blobFileName = id + "#" + uuid;
        String file_url = "";

        try {
            BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                    .connectionString(connectionString)
                    .buildClient();

            BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);

            if (!containerClient.exists()) {
                containerClient.create();
            }

            BlobClient blobClient = containerClient.getBlobClient(blobFileName);

            InputStream fileInputStream = file.getInputStream();

            blobClient.upload(fileInputStream);
            file_url = blobClient.getBlobUrl();
            System.out.println(file_url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuffer result = run_clova(file_url);
        preprocessing(result);
    }

    public StringBuffer run_clova(String file_url) {
        String apiURL = "https://svexlmgbex.apigw.ntruss.com/custom/v1/34998/257c4a121ae568ba528fa07d0d0254b2ae4146912586e65a241e42520b0080ac/general";
        String secretKey = "eXp5Y1BBZG5kZE1QYldpUUVjc3BhZmh3bWF2b0F2bEk=";

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

        public void preprocessing(StringBuffer result) {

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
                    JSONObject field = fieldsArray.getJSONObject(i);
                    String inferText = field.getString("inferText");
                    if(inferText.contains(nutirition)){
                        if(inferText.split(" ").length > 1) {
                            list.add(inferText.split(" ")[1]);
                            break;
                        }

                        else {
                            if (nutirition.equals("kcal")) {
                                JSONObject temp_field = fieldsArray.getJSONObject(i-1);
                                String temp = temp_field.getString("inferText");
                                list.add(temp);
                                break;
                            }
                            else {
                                JSONObject temp_field = fieldsArray.getJSONObject(i+1);
                                String temp = temp_field.getString("inferText");
                                list.add(temp);
                                break;
                            }
                        }
                    }
                    double inferConfidence = field.getDouble("inferConfidence");

                    System.out.println("InferText: " + inferText);
                    System.out.println("InferConfidence: " + inferConfidence);
                    System.out.println();
                        }
                    }
                System.out.println(list);
                for(String nutrition : list) {
                    if(nutrition.contains("g")){
                        String temp = nutrition.replace("g", "");
                        nutrition_array.add(Float.valueOf(temp));
                    }
                    else{
                        nutrition_array.add(Float.valueOf(nutrition));
                    }
                }
                System.out.println(nutrition_array);
                }
            }


