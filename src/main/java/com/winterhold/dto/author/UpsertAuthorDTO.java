package com.winterhold.dto.author;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpsertAuthorDTO {

    private Long id;

    @NotBlank(message = "Nama  tidak Boleh kosong!")
    @Size(max = 10, message = "Tidak Boleh Lebih Dari 100 Karakter")
    private String title;

    @NotNull(message = "nama tidak Boleh kosong!")
    @Size(max = 50, message = "Tidak Boleh Lebih Dari 50 Karakter")
    private String firstName;

//    @NotBlank(message = "Nama  tidak Boleh kosong!")
    @Size(max = 50, message = "Tidak Boleh Lebih Dari 50 Karakter")
    private String lastName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message = "tanggal lahir tidak mungkin dimasa depan!")
    private LocalDate birthDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate deceasedDate;

//    @NotBlank(message = "Nama  tidak Boleh kosong!")
    @Size(max = 50, message = "Tidak Boleh Lebih Dari 50 Karakter")
    private String education;


    @Size(max = 500, message = "Tidak Boleh Lebih Dari 500 Karakter")
    private String summary;



}
