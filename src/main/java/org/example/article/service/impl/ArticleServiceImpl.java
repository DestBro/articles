package org.example.article.service.impl;

import org.example.article.dao.ArticleRepository;
import org.example.article.model.Article;
import org.example.article.service.ArticleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {
    private ArticleRepository repository;

    public ArticleServiceImpl(ArticleRepository repository) {
        this.repository = repository;
    }

    @Override
    public Article save(Article entity) {
        if (entity.getCreationDateTime() == null)
            entity.setCreationDateTime(LocalDateTime.now());

        return repository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Article> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Article> findAll() {
        return (List<Article>) repository.findAll();
    }

    @Override
    public Page<Article> findAll(Pageable pageable) {
        Page<Article> entityPage = repository.findAll(pageable);
        List<Article> entities = entityPage.getContent();
        return new PageImpl<>(entities, pageable, entityPage.getTotalElements());
    }

    @Override
    public Article update(Article entity, Long id) {
        Optional<Article> optional = findById(id);
        if (optional.isPresent()) {
            return save(entity);
        }
        return null;
    }

    @Override
    public List<Article> findAllByCreationDateTimeIsAfter(LocalDateTime creationDateTime) {
        return repository.findAllByCreationDateTimeIsAfter(creationDateTime);
    }
}