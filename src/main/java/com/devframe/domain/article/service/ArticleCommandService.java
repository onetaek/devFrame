package com.devframe.domain.article.service;

import com.devframe.domain.article.dto.proxy.ArticleProxy;
import com.devframe.domain.article.dto.request.ArticleCreateRequest;
import com.devframe.domain.article.dto.request.ArticleServiceCreateRequest;
import com.devframe.domain.article.dto.request.ArticleServiceUpdateRequest;
import com.devframe.domain.article.entity.Article;
import com.devframe.domain.article.exception.ArticleException;
import com.devframe.domain.article.repository.ArticleCommandRepository;
import com.devframe.domain.article.repository.ArticleQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ArticleCommandService {

    private final ArticleCommandRepository articleCommandRepository;
    private final ArticleQueryRepository articleQueryRepository;

    public ArticleProxy create(ArticleServiceCreateRequest request) {
        return ArticleProxy.fromEntity(
                articleCommandRepository.save(
                        ArticleServiceCreateRequest.toEntity(request)
                )
        );
    }

    public List<ArticleProxy> createBatch(List<ArticleServiceCreateRequest> requests) {
        return requests.stream()
                .map(this::create)
                .toList();
    }

    public ArticleProxy update(Long id,ArticleServiceUpdateRequest request) {
        Article updateTarget = articleQueryRepository.findById(id).orElseThrow(ArticleException::notFound);
        return ArticleProxy.fromEntity(
                updateTarget.update(
                        request.getTitle(),
                        request.getContent(),
                        request.getWriter()
                )
        );
    }

    public void delete(Long id) {
        Article deleteTarget = articleQueryRepository.findById(id).orElseThrow(ArticleException::notFound);
        articleCommandRepository.delete(deleteTarget);
    }
}
