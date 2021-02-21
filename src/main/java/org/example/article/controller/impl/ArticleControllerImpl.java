package org.example.article.controller.impl;

import org.example.article.controller.ArticleController;
import org.example.article.dto.ArticleDTO;
import org.example.article.mapper.ArticleMapper;
import org.example.article.model.Article;
import org.example.article.service.ArticleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api/article")
@RestController
public class ArticleControllerImpl implements ArticleController {
    private final ArticleService articleService;
    private final ArticleMapper articleMapper;

    public ArticleControllerImpl(ArticleService articleService, ArticleMapper articleMapper) {
        this.articleService = articleService;
        this.articleMapper = articleMapper;
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ArticleDTO save(@RequestBody @Validated ArticleDTO articleDTO) {
        Article article = articleMapper.asEntity(articleDTO);
        return articleMapper.asDTO(articleService.save(article));
    }

    @Override
    @GetMapping("/{id}")
    public ArticleDTO findById(@PathVariable("id") Long id) {
        Article article = articleService.findById(id).orElse(null);
        return articleMapper.asDTO(article);
    }

    @Override
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        articleService.deleteById(id);
    }

    @Override
    @GetMapping
    public List<ArticleDTO> list() {
        return articleMapper.asDTOList(articleService.findAll());
    }

    @Override
    @GetMapping("/page-query")
    public Page<ArticleDTO> pageQuery(Pageable pageable) {
        Page<Article> articlePage = articleService.findAll(pageable);
        List<ArticleDTO> dtoList = articlePage
                .stream()
                .map(articleMapper::asDTO).collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable, articlePage.getTotalElements());
    }

    @Override
    @PutMapping("/{id}")
    public ArticleDTO update(@RequestBody @Validated ArticleDTO articleDTO, @PathVariable("id") Long id) {
        Article article = articleMapper.asEntity(articleDTO);
        return articleMapper.asDTO(articleService.update(article, id));
    }

    @GetMapping("/countLast7Days")
    public Integer findAllCreated7DaysAgo() {
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        List<Article> allByCreationDateTimeIsAfter = articleService.findAllByCreationDateTimeIsAfter(sevenDaysAgo);
        return allByCreationDateTimeIsAfter.size();
    }
}
