package org.example.article.controller;

import org.example.article.dto.ArticleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ArticleController {
    ArticleDTO save(@RequestBody ArticleDTO article);

    ArticleDTO findById(@PathVariable("id") Long id);

    void delete(@PathVariable("id") Long id);

    List<ArticleDTO> list();

    Page<ArticleDTO> pageQuery(Pageable pageable);

    ArticleDTO update(@RequestBody ArticleDTO dto, @PathVariable("id") Long id);
}