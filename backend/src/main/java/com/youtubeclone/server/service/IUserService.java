package com.youtubeclone.server.service;

import com.youtubeclone.server.model.User;

import java.util.Set;

public interface IUserService {

    public String registerUser(String tokenValue);

    public User getCurrentUser();

    public void addToLikedVideos(String videoId);

    public boolean ifLikedVideo(String videoId);

    public boolean ifDisLikedVideo(String videoId);

    public void removeFromLikedVideos(String videoId);

    public void removeFromDisLikedVideos(String videoId);

    public void addToDisLikedVideos(String videoId);

    public void addVideoToHistory(String videoId);

    public void subscribeUser(String userId);

    public void unSubscribeUser(String userId);

    public Set<String> userHistory(String userId);
}
