package com.youtubeclone.server.service;

import com.youtubeclone.server.model.User;

public interface IUserService {

    public void registerUser(String tokenValue);

    public User getCurrentUser();

    public void addToLikedVideos(String videoId);

    public boolean ifLikedVideo(String videoId);

    public boolean ifDisLikedVideo(String videoId);

    public void removeFromLikedVideos(String videoId);

    public void removeFromDisLikedVideos(String videoId);
}
