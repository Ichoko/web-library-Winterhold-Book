package com.winterhold.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCustomerDTO {
    @NotBlank(message = "Nama company tidak Boleh kosong!")
    @Size(max = 20, message = "Tidak Boleh Lebih Dari 20 Karakter")
    private String membershipNumber;

    @NotBlank(message = "Nama tidak Boleh kosong!")
    @Size(max = 50, message = "Tidak Boleh Lebih Dari 50 Karakter")
    private String firstName;

    @Size(max = 50, message = "Tidak Boleh Lebih Dari 50 Karakter")
    private String lastName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @Size(max = 10, message = "Tidak Boleh Lebih Dari 10 Karakter")
    private String gender;

    @Size(max = 20, message = "Tidak Boleh Lebih Dari 10 Karakter")
    private String phone;

    @Size(max = 500, message = "Tidak Boleh Lebih Dari 500 Karakter")
    private String address;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate membershipExpireDate;


}
