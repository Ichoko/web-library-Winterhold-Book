package com.winterhold.dto.author;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDetailGridDTO {
    private Long id;
    private String fullName;
    private LocalDate birthDate;
    private LocalDate deceasedDate;
    private String education;
    private String summary;
}
