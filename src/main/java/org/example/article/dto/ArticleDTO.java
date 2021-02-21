package org.example.article.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
public class ArticleDTO extends AbstractDTO<Long> {

    private Long id;

    @NotBlank(message = "the 'title' field is required")
    @Size(max = 100)
    private String title;

    @NotBlank(message = "the 'author' filed is required")
    private String author;

    @NotBlank(message = "the 'content' field is required")
    private String content;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonSerialize(using = ToStringSerializer.class)
    private LocalDateTime creationDateTime;
}