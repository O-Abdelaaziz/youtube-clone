package com.youtubeclone.server.model;

import com.youtubeclone.server.enums.VideoStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document("videos")
public class Video {

    @Id
    private String id;
    private String title;
    private String description;
    private String userId;
    private AtomicInteger likes = new AtomicInteger(0);
    private AtomicInteger disLikes = new AtomicInteger(0);
    private Set<String> tags;
    private String videoUrl;
    private VideoStatus videoStatus;
    private AtomicInteger viewCount = new AtomicInteger(0);
    private String thumbnailUrl;
    private List<Comment> comments = new CopyOnWriteArrayList<>();

    public void incrementLikes() {
        this.likes.incrementAndGet();
    }

    public void decrementLikes() {
        this.likes.decrementAndGet();
    }

    public void incrementDisLikes() {
        this.disLikes.incrementAndGet();
    }

    public void decrementDisLikes() {
        this.disLikes.decrementAndGet();
    }

    public void incrementViewCount() {
        this.viewCount.incrementAndGet();
    }

    public void addComments(Comment comment) {
        comments.add(comment);
    }
}
