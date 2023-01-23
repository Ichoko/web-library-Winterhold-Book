package com.winterhold.dto.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookCategoryDTO {
    private String code;
    private String title;
    private String author;
    private String isBorrowed;
    private LocalDate releaseDate;
    private Integer totalPage;
}
