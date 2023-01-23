package com.winterhold.dto.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBookDTO {
    @NotBlank(message = "Code tidak boleh kosong")
    @Size(max = 20, message = "Karakter tidak boleh lebih dari 20")
    private String code;

    @NotBlank(message = "Karakter tidak boleh kosong")
    @Size(max = 100, message = "Karakter tidak boleh lebih dari 100")
    private String title;

    @NotBlank(message = "Category Name tidak boleh kosong")
    @Size(max = 100, message = "Karakter tidak boleh lebih 100")
    private String categoryName;

    @NotNull(message = "Author Id tidak boleh kosong")
    private Long authorId;

    private Boolean isBorrowed;

    @Size(max = 500, message = "Karakter tidak boleh melebihi 500 karakter")
    private String summary;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;

    private Integer totalPage;
}
