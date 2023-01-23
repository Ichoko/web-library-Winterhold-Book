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
public class OneAuthor {
    private Long id;
    private String title;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private LocalDate deceasedDate;
    private String education;
    private String summary;
}
