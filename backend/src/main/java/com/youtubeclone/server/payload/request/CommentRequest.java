package com.youtubeclone.server.payload.request;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentRequest {
    @NotBlank
    private String commentText;
    @NotBlank
    private String commentAuthor;
    @Min(value = 0)
    private int likeCount;
    @Min(value = 0)
    private int disLikeCount;
}
