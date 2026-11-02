package com.etms.backend.controller;

import java.io.IOException;
import java.nio.file.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/files")
public class FileUploadController {

    private final String uploadDir = "uploads";

    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file) throws IOException {
        Files.createDirectories(Paths.get(uploadDir));
        String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path path = Paths.get(uploadDir, filename);
        Files.write(path, file.getBytes());
        return "/api/files/" + filename;
    }

    // FIX: Added ("filename") to the PathVariable
    @GetMapping("/{filename:.+}")
    public byte[] getFile(@PathVariable String filename) throws IOException {
        return Files.readAllBytes(Paths.get(uploadDir, filename));
    }
}