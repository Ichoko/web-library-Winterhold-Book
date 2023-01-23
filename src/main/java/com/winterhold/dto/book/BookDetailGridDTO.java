package com.winterhold.dto.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
//plan==> show authors book
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDetailGridDTO {
    private String code;
    private String title;
    private Long authorId;
    private String isBorrowed;
    private LocalDate releaseDate;
    private Integer totalPage;
    private String categoryName;
}
