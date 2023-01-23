package com.winterhold.dto.loan;


import com.winterhold.entity.Book;
import com.winterhold.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpsertLoanDTO {
    private Long id;
    private String customerNumber;
    private String bookCode;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "kosong!")
    private LocalDate loanDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate returnDate;
    private String note;

}
