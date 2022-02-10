package com.youtubeclone.server.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document("users")
public class User {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String fullName;
    private String picture;
    private String emailAddress;
    private String sub;
    private Set<String> subscribedToUsers = ConcurrentHashMap.newKeySet();
    private Set<String> subscribers = ConcurrentHashMap.newKeySet();
    private Set<String> videoHistory = new LinkedHashSet<>();
    private Set<String> likedVideos = ConcurrentHashMap.newKeySet();
    private Set<String> disLikedVideos = ConcurrentHashMap.newKeySet();


    public void addToLikedVideos(String videoId) {
        likedVideos.add(videoId);
    }

    public void removeFromLikedVideos(String videoId) {
        likedVideos.remove(videoId);
    }

    public void removeFromDisLikedVideos(String videoId) {
        disLikedVideos.remove(videoId);
    }

    public void addToDisLikedVideos(String videoId) {
        disLikedVideos.add(videoId);
    }

    public void addVideoToHistory(String videoId) {
        videoHistory.add(videoId);
    }

    public void addToSubscribedUsers(String userId) {
        subscribedToUsers.add(userId);
    }

    public void addSubscribers(String userId) {
        subscribers.add(userId);
    }

    public void removeFromSubscribeTo(String userId) {
        subscribedToUsers.remove(userId);
    }

    public void removeSubscribers(String userId) {
        subscribers.remove(userId);
    }
}
