package com.winterhold.dao;

import com.winterhold.dto.DropDownDTO;
import com.winterhold.dto.customer.CustomerGridDTO;
import com.winterhold.dto.customer.SingleCustomerDTO;
import com.winterhold.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer,String> {

    @Query("""
            SELECT new com.winterhold.dto.customer.CustomerGridDTO(
                cus.membershipNumber, CONCAT(cus.firstName,' ', cus.lastName), 
                cus.birthDate, cus.gender, cus.phone, cus.address, cus.membershipExpireDate)
            FROM Customer AS cus
            WHERE cus.membershipNumber LIKE %:membershipNumber%
                AND CONCAT(cus.firstName,' ', cus.lastName) LIKE %:fullName%
            """)
    Page<CustomerGridDTO> findByName(@Param("membershipNumber") String membershipNumber,
                                     @Param("fullName") String fullName,
                                     Pageable pageable);
//
    @Query("""
            SELECT new com.winterhold.dto.DropDownDTO(cus.membershipNumber ,CONCAT(cus.firstName,' ', cus.lastName))
            FROM Customer as cus
            """)
    public List<DropDownDTO> getDropDown();


    @Query("""
            SELECT new com.winterhold.dto.customer.SingleCustomerDTO(
                cus.membershipNumber, CONCAT(cus.firstName,' ', cus.lastName), 
                cus.birthDate, cus.gender, cus.phone, cus.address, cus.membershipExpireDate)
            FROM Customer AS cus
            WHERE cus.membershipNumber = :membershipNumber
            """)
    SingleCustomerDTO loanCustomer(@Param("membershipNumber") String membershipNumber);
}
