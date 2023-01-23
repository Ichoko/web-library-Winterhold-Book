package com.winterhold.dto.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCategoryDTO {
    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Floor is required")
    private Integer floor;

    @NotBlank(message = "Isle is required")
    private String isle;

    @NotBlank(message = "Bay is required")
    private String bay;
}
