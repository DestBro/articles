package org.example.article.controller.impl;

import org.example.article.dto.ArticleDTO;
import org.example.article.mapper.ArticleMapper;
import org.example.article.model.Article;
import org.example.article.service.ArticleService;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.nio.charset.Charset;
import java.util.Optional;

import static org.example.article.controller.impl.ArticleBuilder.getNewDTO;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class ArticleControllerImplTest {
    private static final String ENDPOINT_URL = "/api/article";

    @Mock
    private ArticleService articleService;

    @Mock
    private ArticleMapper articleMapper;

    @InjectMocks
    private ArticleControllerImpl articleController;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(articleController)
                .build();
    }

    @Test
    public void getAll() throws Exception {
        when(articleMapper.asDTOList(any())).thenReturn(ArticleBuilder.getListDTO());

        when(articleService.findAll()).thenReturn(ArticleBuilder.getListEntities());
        mockMvc.perform(get(ENDPOINT_URL))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", Matchers.hasSize(2)));
    }

    @Test
    public void getById() throws Exception {
        when(articleMapper.asDTO(any())).thenReturn(ArticleBuilder.getDTO());

        when(articleService.findById(anyLong())).thenReturn(Optional.of(ArticleBuilder.getEntity()));

        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_URL + "/1"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", Is.is(1)));
        verify(articleService, times(1)).findById(1L);
        verifyNoMoreInteractions(articleService);
    }

    @Test
    public void save() throws Exception {
        when(articleMapper.asEntity(any())).thenReturn(ArticleBuilder.getEntity());
        when(articleService.save(any(Article.class))).thenReturn(ArticleBuilder.getEntity());
        mockMvc.perform(
                post(ENDPOINT_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CustomUtils.asJsonString(ArticleBuilder.getDTO())))
                .andExpect(status().isCreated());

        verify(articleService, times(1)).save(any(Article.class));
        verifyNoMoreInteractions(articleService);
    }

    @Test
    public void update() throws Exception {
        when(articleMapper.asEntity(any())).thenReturn(ArticleBuilder.getEntity());
        when(articleService.update(any(), anyLong())).thenReturn(ArticleBuilder.getEntity());

        mockMvc.perform(
                put(ENDPOINT_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CustomUtils.asJsonString(ArticleBuilder.getDTO())))
                .andExpect(status().isOk());
        verify(articleService, times(1)).update(any(Article.class), anyLong());
        verifyNoMoreInteractions(articleService);
    }

    @Test
    public void delete() throws Exception {
        doNothing().when(articleService).deleteById(anyLong());
        mockMvc.perform(
                MockMvcRequestBuilders.delete(ENDPOINT_URL + "/1"))
                .andExpect(status().isOk());
        verify(articleService, times(1)).deleteById(anyLong());
        verifyNoMoreInteractions(articleService);
    }

    @Test
    public void givenArticleWithNullContent_shouldFailValidation() throws Exception {
        ArticleDTO newArticleDTO = getNewDTO();
        newArticleDTO.setContent(null);

        when(articleMapper.asEntity(any())).thenReturn(ArticleBuilder.getEntity());
        mockMvc.perform(
                post(ENDPOINT_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CustomUtils.asJsonString(newArticleDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result -> assertTrue(result.getResolvedException().getLocalizedMessage()
                        .contains("the 'content' field is required")));

        verifyNoMoreInteractions(articleService);
    }

    @Test
    public void givenArticleWithOverlongTitle_shouldFailValidation() throws Exception {
        ArticleDTO newArticleDTO = getNewDTO();
        newArticleDTO.setTitle(new String(new byte[101], Charset.forName("UTF-8")));

        when(articleMapper.asEntity(any())).thenReturn(ArticleBuilder.getEntity());
        mockMvc.perform(
                post(ENDPOINT_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CustomUtils.asJsonString(newArticleDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(result -> assertTrue(result.getResolvedException().getLocalizedMessage()
                        .contains("size must be between 0 and 100")));

        verifyNoMoreInteractions(articleService);
    }

}