package com.winterhold.dto.loan;

import com.winterhold.entity.Book;
import com.winterhold.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SingleLoanDTO {
    private Long id;
    private String customerNumber;
    private String bookCode;
    private LocalDate loanDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private String note;
}
