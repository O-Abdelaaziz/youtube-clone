package com.youtubeclone.server.controller;

import com.youtubeclone.server.payload.request.VideoRequest;
import com.youtubeclone.server.service.IVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public void uploadVideo(@RequestParam(value = "file") MultipartFile file) {
        iVideoService.uploadVideo(file);
    }

    @PostMapping("/upload-thumbnail")
    @ResponseStatus(HttpStatus.CREATED)
    public String uploadThumbnail(@RequestParam(value = "file") MultipartFile file, @RequestParam("videoId") String videoId) {
         return iVideoService.uploadThumbnail(file,videoId);
    }

    @PutMapping("/update-video")
    @ResponseStatus(HttpStatus.OK)
    public VideoRequest updateVideoDetails(@RequestBody VideoRequest videoRequest) {
        return iVideoService.updateVideo(videoRequest);
    }
}
