package org.example.article.mapper;

import org.example.article.dto.ArticleDTO;
import org.example.article.model.Article;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ReferenceMapper.class)
public interface ArticleMapper extends GenericMapper<Article, ArticleDTO> {
    @Override
    @Mapping(target = "id", ignore = false)
    Article asEntity(ArticleDTO dto);
}