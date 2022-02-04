package com.youtubeclone.server.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document("comments")
public class Comment {
    @Id
    private String id;
    private String text;
    private String author;
    private Integer likeCount;
    private Integer disLikeCount ;
}
