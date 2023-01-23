package com.winterhold.dto.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryGridDTO {
    private String name;
    private Integer floor;
    private String isle;
    private String bay;

    private Integer totalBook;

    public CategoryGridDTO(String name, Integer floor, String isle, String bay) {
        this.name = name;
        this.floor = floor;
        this.isle = isle;
        this.bay = bay;
    }
}
