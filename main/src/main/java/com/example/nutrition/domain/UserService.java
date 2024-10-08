package com.example.nutrition.domain;

import com.azure.storage.blob.*;
import com.azure.storage.blob.models.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.*;
import java.util.UUID;
import java.util.Properties;
import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Value("${custom.api.key}")
    private String connectionString;

    public boolean checkLoginIdDuplicate(String loginId) {
        return userRepository.existsByLoginId(loginId);
    }

    public void join(JoinRequest req) {
        if (!checkLoginIdDuplicate(req.getLoginId())) {
            userRepository.save(req.toEntity());
        }
    }

    public String login(LoginRequest req) {
        Optional<User> optionalUser = userRepository.findByLoginId(req.getLoginId());

        if(optionalUser.isEmpty()) {
            return null;
        }

        User user = optionalUser.get();
        System.out.println(user);

        if(!user.getPassword().equals(req.getPassword())) {
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
        runPython(file_url);
    }

    public void runPython(String file_url) throws IOException, InterruptedException {
        Process p = null;
        String[] command = {"python", "C:\\\\Users\\\\woo28\\\\Desktop\\\\nutrition2\\\\main\\\\testOCR2.py", file_url};
        Runtime runtime = Runtime.getRuntime();

        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long usedMemory = totalMemory - freeMemory;

        System.out.println(totalMemory);
        System.out.println(freeMemory);
        System.out.println(usedMemory);


        try {
            p = runtime.exec(command);
        } catch (IOException e) {
            System.err.println("Error executing calc.");
        }

        p.waitFor();
        BufferedReader bri = new BufferedReader(new InputStreamReader(p.getInputStream()));
        BufferedReader bre = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        String line;
        while ((line = bri.readLine()) != null) {
            System.out.println(line);
        }
        bri.close();
        while ((line = bre.readLine()) != null) {
            System.out.println(line);
        }
        bre.close();
        p.waitFor();
        System.out.println("Done.");

        p.destroy();
    }
}


//        try {
//            // 실행할 Python 파일 경로
//            String pythonFilePath = "C:\\Users\\woo28\\Desktop\\nutrition2\\main\\testOCR2.py";
//
//            // 파이썬 실행 명령어 생성 (Python 3 사용 시 "python3"로 변경)
//            List<String> command = new ArrayList<>();
//            command.add("python3");  // 또는 "python3"
//            command.add(pythonFilePath);
//
//            // ProcessBuilder 생성 및 실행
//            ProcessBuilder processBuilder = new ProcessBuilder(command);
//            Process process = processBuilder.start();
//
//            // Python 스크립트 출력 읽기
//            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//            String line;
//            while ((line = reader.readLine()) != null) {
//                System.out.println(line);  // Python에서 출력된 내용을 Java에서 출력
//            }
//
//            // 프로세스 종료 대기 및 종료 코드 확인
//            int exitCode = process.waitFor();
//            System.out.println("Python script exited with code: " + exitCode);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }