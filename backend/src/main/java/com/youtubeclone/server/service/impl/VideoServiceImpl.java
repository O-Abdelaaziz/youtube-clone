package com.youtubeclone.server.service.impl;

import com.youtubeclone.server.model.Video;
import com.youtubeclone.server.repository.VideoRepository;
import com.youtubeclone.server.service.IFileService;
import com.youtubeclone.server.service.IVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class VideoServiceImpl implements IVideoService {

    private VideoRepository videoRepository;
    private IFileService is3Service;

    @Autowired
    public VideoServiceImpl(VideoRepository videoRepository, IFileService is3Service) {
        this.videoRepository = videoRepository;
        this.is3Service = is3Service;
    }

    @Override
    public void uploadVideo(MultipartFile multipartFile) {
        String videoUrl = is3Service.uploadFile(multipartFile);
        Video video = new Video();
        video.setUrl(videoUrl);
        videoRepository.save(video);
    }
}
