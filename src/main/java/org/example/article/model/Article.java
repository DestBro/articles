package org.example.article.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Data
public class Article implements AbstractEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "the 'title' field is required")
    @Column(length = 100)
    @Size(max = 100)
    private String title;

    @NotBlank(message = "the 'author' filed is required")
    private String author;

    @NotBlank(message = "the 'content' field is required")
    @Column(length = 10000)
    private String content;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonSerialize(using = ToStringSerializer.class)
    private LocalDateTime creationDateTime;

    @Override
    public Long getId() {
        return id;
    }
}
