package com.winterhold.dto.loan;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoanBookDetail {
    private String code;
    private String title;
    private String categoryName;
    private String authorName;
    private Integer floor;
    private String isle;
    private String bay;
}
