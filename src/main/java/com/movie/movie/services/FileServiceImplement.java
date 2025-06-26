package com.movie.movie.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileServiceImplement implements FileService {
    @Override
    public String uploadFile(String path, MultipartFile file) throws IOException {

        System.out.println("Uploading file: " + path);

        String fileName= file.getOriginalFilename();

        String filePath=path+ File.separator+fileName;
        System.out.println("Saving file to: " + filePath);


        File f=new File(path);

        if(!f.exists()){
            f.mkdir();
        }

        Files.copy(file.getInputStream(), Paths.get(filePath) , StandardCopyOption.REPLACE_EXISTING);
        return fileName;
    }

    @Override
    public InputStream getResourcesFile(String path, String name) throws FileNotFoundException {

        String filePath=path+ File.separator+name;
        return new FileInputStream(filePath);
    }
}
