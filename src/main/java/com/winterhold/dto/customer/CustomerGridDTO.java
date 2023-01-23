package com.winterhold.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerGridDTO {
    private String membershipNumber;
    private String fullName;
    private LocalDate birthDate;
    private String gender;
    private String phone;
    private String address;
    private LocalDate membershipExpireDate=LocalDate.now().plusYears(2);
}
