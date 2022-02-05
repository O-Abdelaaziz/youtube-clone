package com.youtubeclone.server.service;

import com.youtubeclone.server.payload.request.VideoRequest;
import org.springframework.web.multipart.MultipartFile;

public interface IVideoService {

    public void uploadVideo(MultipartFile multipartFile);

    public VideoRequest updateVideo(VideoRequest videoRequest);
}
