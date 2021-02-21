package org.example.article.controller.impl;

import org.assertj.core.util.Lists;
import org.example.article.dto.ArticleDTO;
import org.example.article.model.Article;

import java.time.LocalDateTime;
import java.util.List;

public class ArticleBuilder {

    final static private List<ArticleDTO> articleDTOs = Lists.newArrayList();
    final static private List<Article> articles = Lists.newArrayList();

    static {
        for (long i = 1; i < 3; i++) {
            ArticleDTO articleDTO = new ArticleDTO();
            articleDTO.setId(i);
            articleDTO.setAuthor("Author " + i);
            articleDTO.setTitle("Title " + i);
            articleDTO.setContent("Content " + i);
            articleDTO.setCreationDateTime(LocalDateTime.now());
            articleDTOs.add(articleDTO);

            Article article = new Article();
            article.setId(i);
            article.setAuthor("Author " + i);
            article.setTitle("Title " + i);
            article.setContent("Content " + i);
            article.setCreationDateTime(LocalDateTime.now());
            articles.add(article);
        }
    }

    public static List<ArticleDTO> getListDTO() {
        return articleDTOs;
    }

    public static List<Article> getListEntities() {
        return articles;
    }

    public static ArticleDTO getDTO() {
        return articleDTOs.get(0);
    }

    public static Article getEntity() {
        return articles.get(0);
    }

    public static ArticleDTO getNewDTO() {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(1L);
        articleDTO.setAuthor("Author");
        articleDTO.setTitle("Title");
        articleDTO.setContent("Content");
        articleDTO.setCreationDateTime(LocalDateTime.now());
        return articleDTO;
    }
}
