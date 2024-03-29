package com.devframe.domain.article.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ArticleUpdateRequest {
    private String title;
    private String content;
    private String writer;

    public static ArticleServiceUpdateRequest toServiceRequest(ArticleUpdateRequest request) {
        return ArticleServiceUpdateRequest.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .writer(request.getWriter())
                .build();
    }
}
