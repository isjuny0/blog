package com.example.blog.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FileUploadResponse {

    private String fileName;
    private String downloadUrl;
}
