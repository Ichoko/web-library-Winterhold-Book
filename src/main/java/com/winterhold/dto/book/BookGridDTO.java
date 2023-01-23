package com.winterhold.dto.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
// plan =>> To show all book
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookGridDTO {
    private String code;
    private String title;
    private String authorName;
    private String isBorrowed;
    private LocalDate releaseDate;
    private Integer totalPage;
}
