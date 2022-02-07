package com.youtubeclone.server.service.impl;

import com.youtubeclone.server.model.Video;
import com.youtubeclone.server.payload.response.VideoResponse;
import com.youtubeclone.server.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Transactional
@Service
public class LocalStorageService {

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private VideoRepository videoRepository;

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(Paths.get(uploadPath));
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload folder!");
        }
    }

    public VideoResponse uploadVideo(MultipartFile multipartFile) {
        try {
            Path root = Paths.get(uploadPath);
            if (!Files.exists(root)) {
                init();
            }
            Files.copy(multipartFile.getInputStream(), root.resolve(multipartFile.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
            Video video = new Video();
            video.setVideoUrl(multipartFile.getOriginalFilename());
            videoRepository.save(video);
            VideoResponse videoResponse = new VideoResponse();
            videoResponse.setVideoId(video.getId());
            videoResponse.setVideoUrl(multipartFile.getOriginalFilename());
            return videoResponse;

        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    public String uploadThumbnail(MultipartFile file, String videoId) {
        Video getVideo = getVideoById(videoId);
        Path root = Paths.get(uploadPath);
        if (!Files.exists(root)) {
            init();
        }
        try {
            Files.copy(file.getInputStream(), root.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
        String thumbnailUrl = file.getOriginalFilename();
        getVideo.setThumbnailUrl(thumbnailUrl);
        videoRepository.save(getVideo);
        return thumbnailUrl;
    }

    public Video getVideoById(String videoId) {
        return videoRepository.findById(videoId)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find video by id - " + videoId));
    }
}
