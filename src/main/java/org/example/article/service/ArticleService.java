package org.example.article.service;

import org.example.article.model.Article;
import org.springframework.security.access.prepost.PreAuthorize;

import java.time.LocalDateTime;
import java.util.List;

public interface ArticleService extends GenericService<Article, Long> {

    @PreAuthorize("authenticated")
    List<Article> findAllByCreationDateTimeIsAfter(LocalDateTime creationDateTime);
}