package com.movie.movie.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface FileService {

    String uploadFile(String path, MultipartFile file) throws IOException;

    InputStream getResourcesFile(String path, String name) throws FileNotFoundException;

}
