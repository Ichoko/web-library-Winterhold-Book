package com.winterhold.dto.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.relational.core.sql.In;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpsertCategoryDTO {

    @NotBlank(message = "Nama  tidak Boleh kosong!")
    @Size(max = 100, message = "Tidak Boleh Lebih Dari 100 Karakter")
    private String name;

    @NotNull(message = "floor tidak Boleh kosong!")
    private Integer floor;

    @NotBlank(message = "Nama  tidak Boleh kosong!")
    @Size(max = 10, message = "Tidak Boleh Lebih Dari 10 Karakter")
    private String isle;

    @NotBlank(message = "Nama  tidak Boleh kosong!")
    @Size(max = 10, message = "Tidak Boleh Lebih Dari 10 Karakter")
    private String bay;



}
