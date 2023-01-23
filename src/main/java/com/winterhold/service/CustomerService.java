package com.winterhold.service;

import com.winterhold.dao.CustomerRepository;
import com.winterhold.dto.customer.CustomerGridDTO;
import com.winterhold.dto.customer.UpdateCustomerDTO;
import com.winterhold.dto.customer.InsertCustomerDTO;
import com.winterhold.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Year;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public Page<CustomerGridDTO> getCustomerGrid(Integer pageNumber, String membershipNumber, String fullName){
        var pageable = PageRequest.of(pageNumber -1,10, Sort.by("membershipNumber"));
        var  hasilGrid = customerRepository.findByName(membershipNumber, fullName, pageable);
        return hasilGrid;
    }

    public CustomerGridDTO getOneCustomer(String membershipNumber){
        Customer entity = customerRepository.findById(membershipNumber).get();
        var dto = new CustomerGridDTO(
                entity.getMembershipNumber(),
                entity.getFirstName() + entity.getLastName(),
                entity.getBirthDate(),
                entity.getGender(),
                entity.getPhone(),
                entity.getAddress(),
                entity.getMembershipExpireDate()
        );
        return dto;
    }

    public Customer saveCustomer(InsertCustomerDTO dto) {
        LocalDate expiredDate = null;
        if(dto.getMembershipExpireDate() == null){
            expiredDate = LocalDate.now().plusYears(2);
        } else {
            expiredDate = dto.getMembershipExpireDate();
        }
        Customer entity = new Customer(
                dto.getMembershipNumber(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getBirthDate(),
                dto.getGender(),
                dto.getPhone(),
                dto.getAddress(),
                expiredDate
        );
        return customerRepository.save(entity);
    }
    public UpdateCustomerDTO getUpdate(String membershipNumber){
        var customer = customerRepository.findById(membershipNumber).get();
        UpdateCustomerDTO dto = new UpdateCustomerDTO(
                customer.getMembershipNumber(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getBirthDate(),
                customer.getGender(),
                customer.getPhone(),
                customer.getAddress(),
                customer.getMembershipExpireDate()
        );
        return dto;
    }

    public Customer updateCustomer(UpdateCustomerDTO dto) {

        Customer entity = new Customer(
                dto.getMembershipNumber(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getBirthDate(),
                dto.getGender(),
                dto.getPhone(),
                dto.getAddress(),
                dto.getMembershipExpireDate()
        );
        return customerRepository.save(entity);
    }

    public void delete(String membershipNumber) {
        customerRepository.deleteById(membershipNumber);
    }

}
