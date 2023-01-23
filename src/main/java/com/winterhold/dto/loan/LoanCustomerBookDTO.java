package com.winterhold.dto.loan;

import com.winterhold.dto.book.SingleBookDTO;
import com.winterhold.dto.customer.SingleCustomerDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoanCustomerBookDTO {
    private LoanBookDetail book;
    private SingleCustomerDTO customer;
}
