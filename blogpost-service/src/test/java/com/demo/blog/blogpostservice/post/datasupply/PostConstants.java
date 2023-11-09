package com.demo.blog.blogpostservice.post.datasupply;

import com.demo.blog.blogpostservice.post.dto.PostResponse;

import java.time.LocalDateTime;
import java.util.Comparator;

public class PostConstants {

    public static final String ID_NOT_FOUND_MSG_T = "Post of '%d' ID was not found";
    public static final String NULL_ID_MSG = "Post ID was null";

    public static final Comparator<PostResponse> PUBLISHED_DESC_COMPARATOR = (p1, p2) -> {
        LocalDateTime d1 = LocalDateTime.parse(p1.publishedOn());
        LocalDateTime d2 = LocalDateTime.parse(p2.publishedOn());
        return d2.compareTo(d1);
    };
}
