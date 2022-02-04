package com.youtubeclone.server.service;

import org.springframework.web.multipart.MultipartFile;

public interface IVideoService {

    public void uploadVideo(MultipartFile multipartFile);
}
