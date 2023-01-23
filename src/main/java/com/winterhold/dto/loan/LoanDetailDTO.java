package com.winterhold.dto.loan;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoanDetailDTO {
    private String title;
    private String categoryName;
    private String authorName;
    private Integer floor;
    private String isle;
    private String bay;

}
