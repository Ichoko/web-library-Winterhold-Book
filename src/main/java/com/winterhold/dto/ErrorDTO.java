package com.winterhold.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter @Getter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDTO {
    private String jenisExeption;
    private String messege;
    private LocalDateTime waktuError;

}
