package com.winterhold.dto.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BooksDetailDTO {
    private String code;
    private String title;
    private String categoryName;
    private String authorName;
    private String isBorrowed;
    private String summary;
    private LocalDate releaseDate;
    private Integer totalPage;
}
