package com.winterhold.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

//@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SayaDTO {

    private String name ;
    private LocalDate tanggalLahir ;
    private String kampus ;

}
