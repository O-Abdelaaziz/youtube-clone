package com.youtubeclone.server.service.impl;

import com.youtubeclone.server.model.Comment;
import com.youtubeclone.server.model.Video;
import com.youtubeclone.server.payload.request.CommentRequest;
import com.youtubeclone.server.payload.request.VideoRequest;
import com.youtubeclone.server.payload.response.VideoResponse;
import com.youtubeclone.server.repository.VideoRepository;
import com.youtubeclone.server.service.IFileService;
import com.youtubeclone.server.service.IUserService;
import com.youtubeclone.server.service.IVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class VideoServiceImpl implements IVideoService {

    private VideoRepository videoRepository;
    private IFileService is3Service;
    private IUserService iUserService;

    @Autowired
    public VideoServiceImpl(VideoRepository videoRepository, IFileService is3Service, IUserService iUserService) {
        this.videoRepository = videoRepository;
        this.is3Service = is3Service;
        this.iUserService = iUserService;
    }

    @Override
    public VideoResponse uploadVideo(MultipartFile multipartFile) {
        String videoUrl = is3Service.uploadFile(multipartFile);
        Video video = new Video();
        video.setVideoUrl(videoUrl);
        videoRepository.save(video);
        VideoResponse videoResponse = new VideoResponse();
        videoResponse.setVideoId(video.getId());
        videoResponse.setVideoUrl(videoUrl);
        return videoResponse;
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

    @Override
    public VideoRequest getVideoDetails(String videoId) {
        Video getVideo = getVideoById(videoId);

        increaseVideoViewCount(getVideo);
        iUserService.addVideoToHistory(videoId);

        VideoRequest videoRequest = mapToVideoRequest(getVideo);
        return videoRequest;
    }

    private void increaseVideoViewCount(Video getVideo) {
        getVideo.incrementViewCount();
        videoRepository.save(getVideo);
    }

    @Override
    public VideoRequest likeVideo(String videoId) {
        Video getVideo = getVideoById(videoId);

        if (iUserService.ifLikedVideo(videoId)) {
            getVideo.decrementLikes();
            iUserService.removeFromLikedVideos(videoId);
        } else if (iUserService.ifDisLikedVideo(videoId)) {
            getVideo.decrementDisLikes();
            iUserService.removeFromDisLikedVideos(videoId);
            getVideo.incrementLikes();
            iUserService.addToLikedVideos(videoId);
        } else {
            getVideo.incrementLikes();
            iUserService.addToLikedVideos(videoId);
        }

        videoRepository.save(getVideo);

        VideoRequest videoRequest = mapToVideoRequest(getVideo);
        return videoRequest;
    }

    @Override
    public VideoRequest disLikeVideo(String videoId) {
        Video getVideo = getVideoById(videoId);

        if (iUserService.ifDisLikedVideo(videoId)) {
            getVideo.decrementDisLikes();
            iUserService.removeFromDisLikedVideos(videoId);
        } else if (iUserService.ifLikedVideo(videoId)) {
            getVideo.decrementLikes();
            iUserService.removeFromLikedVideos(videoId);
            getVideo.incrementDisLikes();
            iUserService.addToDisLikedVideos(videoId);
        } else {
            getVideo.incrementDisLikes();
            iUserService.addToDisLikedVideos(videoId);
        }

        videoRepository.save(getVideo);

        VideoRequest videoRequest = mapToVideoRequest(getVideo);
        return videoRequest;
    }

    @Override
    public void addComment(String videoId, CommentRequest commentRequest) {
        Video video = getVideoById(videoId);
        Comment comment = new Comment();
        comment.setText(commentRequest.getCommentText());
        comment.setAuthor(commentRequest.getCommentAuthor());
        video.addComments(comment);
        videoRepository.save(video);
    }

    @Override
    public List<CommentRequest> getAllComments(String videoId) {
        Video video = getVideoById(videoId);
        List<Comment> commentList = video.getComments();
        return commentList.stream().map(this::mapToCommentRequest)
                .collect(Collectors.toList());
    }

    @Override
    public List<VideoRequest> getAllVideos() {
        return videoRepository.findAll().stream().map(
                this::mapToVideoRequest
        ).collect(Collectors.toList());
    }

    private CommentRequest mapToCommentRequest(Comment comment) {
        CommentRequest commentRequest = new CommentRequest();
        commentRequest.setCommentText(comment.getText());
        commentRequest.setCommentAuthor(comment.getAuthor());
        return commentRequest;
    }

    private VideoRequest mapToVideoRequest(Video getVideo) {
        VideoRequest videoRequest = new VideoRequest();
        videoRequest.setId(getVideo.getId());
        videoRequest.setTitle(getVideo.getTitle());
        videoRequest.setDescription(getVideo.getDescription());
        videoRequest.setTags(getVideo.getTags());
        videoRequest.setVideoStatus(getVideo.getVideoStatus());
        videoRequest.setVideoUrl(getVideo.getVideoUrl());
        videoRequest.setThumbnailUrl(getVideo.getThumbnailUrl());
        videoRequest.setLikedCount(getVideo.getLikes().get());
        videoRequest.setDisLikedCount(getVideo.getDisLikes().get());
        videoRequest.setViewCount(getVideo.getViewCount().get());
        return videoRequest;
    }

    /**
     * likeVideo
     * incerement like count
     * if user already liked the video, then decrement like count
     * if user already disliked the video, then increment like count and decrement dislike count
     */
}
