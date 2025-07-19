package com.example.blog.service;

import com.example.blog.dto.FileUploadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@RequiredArgsConstructor
public class FileService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    public FileUploadResponse uploadFile(MultipartFile file) throws IOException {
        // 디렉토리 생성
        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        Files.createDirectories(uploadPath);

        // 파일명 정리 및 저장
        String originalName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileName = System.currentTimeMillis() + "." + originalName;
        Path targetLocation = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        return new FileUploadResponse(
                fileName,
                "/api/files/download/" + fileName
        );
    }

    public Resource loadFile(String fileName) throws MalformedURLException {
        Path filePath = Paths.get(uploadDir).toAbsolutePath().normalize().resolve(fileName).normalize();
        Resource resource = new UrlResource(filePath.toUri());

        if (!resource.exists()) {
            throw new RuntimeException("파일을 찾을 수 없습니다: " + fileName);
        }

        return resource;
    }
}
