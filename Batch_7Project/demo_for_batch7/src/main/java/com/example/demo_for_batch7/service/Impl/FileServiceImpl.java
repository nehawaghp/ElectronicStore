package com.example.demo_for_batch7.service.Impl;

import com.example.demo_for_batch7.exception.BadApiRequest;
import com.example.demo_for_batch7.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl  implements FileService {

    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Override
    public String uploadFile(MultipartFile file, String path) throws IOException {
        String originalFilename = file.getOriginalFilename();
        logger.info("filename :{}", originalFilename);
        String filename = UUID.randomUUID().toString();
        String extensions = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileNameWithExtensions = filename + extensions;
        String fullPathWithFileName = path + File.pathSeparator + fileNameWithExtensions;

        if (extensions.equalsIgnoreCase(".png") || extensions.equalsIgnoreCase(".jpeg") || extensions.equalsIgnoreCase(".jpa")) {

            // file save
            File folder = new File(path);
            if (!folder.mkdirs()) {

                //create the folder
                folder.mkdirs();
            }
            //upload

            Files.copy(file.getInputStream(), Paths.get(fullPathWithFileName));
            return fileNameWithExtensions;
        } else {
            throw new BadApiRequest("file with this" + extensions + "not allowed !!");

        }
    }

    @Override
    public InputStream getResource(String path, String name) throws FileNotFoundException {
        String fullpath = path + File.separator + name;
        InputStream inputStream = new FileInputStream(fullpath);
        return inputStream;
    }
}
