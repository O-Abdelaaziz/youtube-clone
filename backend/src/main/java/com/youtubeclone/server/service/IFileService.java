package com.youtubeclone.server.service;

import org.springframework.web.multipart.MultipartFile;

public interface IFileService {
    public String uploadFile(MultipartFile multipartFile);
}
