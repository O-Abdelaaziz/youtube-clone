package com.youtubeclone.server.service.impl;

import com.youtubeclone.server.model.Video;
import com.youtubeclone.server.payload.request.VideoRequest;
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
        video.setVideoUrl(videoUrl);
        videoRepository.save(video);
    }

    @Override
    public VideoRequest updateVideo(VideoRequest videoRequest) {
        Video getVideo = getVideoById(videoRequest.getId());
        getVideo.setTitle(videoRequest.getTitle());
        getVideo.setDescription(videoRequest.getDescription());
        getVideo.setTags(videoRequest.getTags());
        getVideo.setVideoUrl(videoRequest.getVideoUrl());
        getVideo.setVideoStatus(videoRequest.getVideoStatus());
        getVideo.setThumbnailUrl(videoRequest.getThumbnailUrl());
        videoRepository.save(getVideo);
        return videoRequest;
    }

    @Override
    public String uploadThumbnail(MultipartFile file, String videoId) {
        Video getVideo = getVideoById(videoId);
        String thumbnailUrl = is3Service.uploadFile(file);
        getVideo.setThumbnailUrl(thumbnailUrl);
        videoRepository.save(getVideo);
        return thumbnailUrl;
    }

    @Override
    public Video getVideoById(String videoId) {
        return videoRepository.findById(videoId)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find video by id - " + videoId));
    }
}
