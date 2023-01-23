package com.winterhold.dto.author;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;


@NoArgsConstructor
@AllArgsConstructor
public class AuthorGridDTO {

    @Getter
    @Setter
    private Long id;
    @Getter
    @Setter
    private String fullName;

    private LocalDate age ;

    @Getter
    @Setter
    private String status;
    @Getter
    @Setter
    private String education;
    private LocalDate deceasedDate;

    public Long getAge() {
        if (deceasedDate== null){
            return ChronoUnit.YEARS.between(age,LocalDate.now());
        } else {
            return ChronoUnit.YEARS.between(age, deceasedDate);
        }
    }

}
