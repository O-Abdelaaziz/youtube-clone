package com.youtubeclone.server.controller;

import com.youtubeclone.server.payload.request.CommentRequest;
import com.youtubeclone.server.payload.request.VideoRequest;
import com.youtubeclone.server.payload.response.VideoResponse;
import com.youtubeclone.server.service.IVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/videos")
public class VideoController {

    private IVideoService iVideoService;

    @Autowired
    public VideoController(IVideoService iVideoService) {
        this.iVideoService = iVideoService;
    }

    @PostMapping("/upload-video")
    @ResponseStatus(HttpStatus.CREATED)
    public VideoResponse uploadVideo(@RequestParam(value = "file") MultipartFile file) {
        return iVideoService.uploadVideo(file);
    }

    @PostMapping("/upload-thumbnail")
    @ResponseStatus(HttpStatus.CREATED)
    public String uploadThumbnail(@RequestParam(value = "file") MultipartFile file, @RequestParam("videoId") String videoId) {
        return iVideoService.uploadThumbnail(file, videoId);
    }

    @PutMapping("/update-video")
    @ResponseStatus(HttpStatus.OK)
    public VideoRequest updateVideoDetails(@RequestBody VideoRequest videoRequest) {
        return iVideoService.updateVideo(videoRequest);
    }

    @GetMapping("/{videoId}")
    @ResponseStatus(HttpStatus.OK)
    public VideoRequest getVideoDetails(@PathVariable(value = "videoId") String videoId) {
        return iVideoService.getVideoDetails(videoId);
    }

    @PostMapping("/{videoId}/like")
    @ResponseStatus(HttpStatus.OK)
    public VideoRequest likeVideo(@PathVariable("videoId") String videoId) {
        return iVideoService.likeVideo(videoId);
    }

    @PostMapping("/{videoId}/dislike")
    @ResponseStatus(HttpStatus.OK)
    public VideoRequest disLikeVideo(@PathVariable("videoId") String videoId) {
        return iVideoService.disLikeVideo(videoId);
    }

    @PostMapping("/{videoId}/comment")
    @ResponseStatus(HttpStatus.OK)
    public void addComment(@PathVariable("videoId") String videoId, @RequestBody CommentRequest commentRequest) {
        iVideoService.addComment(videoId, commentRequest);
    }

    @GetMapping("/{videoId}/comment")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentRequest> listComments(@PathVariable("videoId") String videoId) {
        return iVideoService.getAllComments(videoId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<VideoRequest> listVideos() {
        return iVideoService.getAllVideos();
    }

}
