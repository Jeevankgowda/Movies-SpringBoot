package com.movie.movie.controllers;

import com.movie.movie.services.FileService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/file/")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @Value("${project.posterPath}")
    private String path;


    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestPart MultipartFile file) throws IOException
    {
        String fileName=fileService.uploadFile(path, file);

        return ResponseEntity.ok("File Uploaded Successfully"+fileName);

    }

    @GetMapping("/{filename}")

    public void getFile(@PathVariable String filename, HttpServletResponse response) throws IOException {

        InputStream str= fileService.getResourcesFile(path, filename);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(str, response.getOutputStream());
    }
}
