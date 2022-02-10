package com.youtubeclone.server.service;

import com.youtubeclone.server.model.Video;
import com.youtubeclone.server.payload.request.CommentRequest;
import com.youtubeclone.server.payload.request.VideoRequest;
import com.youtubeclone.server.payload.response.VideoResponse;
import org.springframework.web.multipart.MultipartFile;

public interface IVideoService {

    public VideoResponse uploadVideo(MultipartFile multipartFile);

    public VideoRequest updateVideo(VideoRequest videoRequest);

    public String uploadThumbnail(MultipartFile file, String videoId);

    public Video getVideoById(String videoId);

    public VideoRequest getVideoDetails(String videoId);

    public VideoRequest likeVideo(String videoId);

    public VideoRequest disLikeVideo(String videoId);

    public void addComment(String videoId, CommentRequest commentRequest);
}
