package com.example.demo_for_batch7.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface FileService {

     String uploadFile(MultipartFile file, String path) throws IOException;
    InputStream getResource(String path, String name) throws FileNotFoundException;
}
