package com.winterhold.service;

import com.winterhold.dao.BookRepository;
import com.winterhold.dao.CustomerRepository;
import com.winterhold.dao.LoanRepository;
import com.winterhold.dto.DropDownDTO;
import com.winterhold.dto.loan.*;
import com.winterhold.entity.Loan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LoanService {
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CustomerRepository customerRepository;

    public Page<LoanGridDTO> getLoanGrid(Integer pageNumber, String title, String customerName){
        var pageable = PageRequest.of(pageNumber -1, 10, Sort.by("id"));
        var hasilGrid = loanRepository.findTitleName(title, customerName, pageable);
        return hasilGrid;
    }
    public LoanDetailDTO getLoanBook(String code){
        return bookRepository.getLoanBook(code);
    }


    public LoanCustomerBookDTO loanDetail(Long id){
        var loan = loanRepository.findById(id).get();
        var book = bookRepository.loanBook(loan.getBookCode());
        var customer = customerRepository.loanCustomer(loan.getCustomerNumber());
        return new LoanCustomerBookDTO(book,customer);
    }
    public Loan getUpdate(UpsertLoanDTO dto){
        var entity = new Loan(
                dto.getId(),
                dto.getCustomerNumber(),
                dto.getBookCode(),
                dto.getLoanDate(),
                dto.getDueDate(),
                dto.getReturnDate(),
                dto.getNote()
        );
        loanRepository.save(entity);
        return entity;
    }

    public UpsertLoanDTO getDTOForm(Long id){
        var entity = loanRepository.findById(id).get();
        var dto = new UpsertLoanDTO(
                entity.getId(),
                entity.getCustomerNumber(),
                entity.getBookCode(),
                entity.getLoanDate(),
                entity.getDueDate(),
                entity.getReturnDate(),
                entity.getNote()
        );
     return dto;
    }


//    Sama Nanti dengan Entity Loan<<
    public SingleLoanDTO getSingleLoan(Long id){
        var entity = loanRepository.findById(id).get();
        var dto = new SingleLoanDTO(
                entity.getId(),
                entity.getCustomerNumber(),
                entity.getBookCode(),
                entity.getLoanDate(),
                entity.getDueDate(),
                entity.getReturnDate(),
                entity.getNote()
        );
        return dto;
    }

    public Loan save(UpsertLoanDTO dto){
        LocalDate nullableDueDate = dto.getDueDate();
        LocalDate dueDate;
        if(nullableDueDate == null){
            dueDate = dto.getLoanDate().plusDays(5);
        } else {
            dueDate = dto.getDueDate();
        }
        var entity = new Loan(
                dto.getId(),
                dto.getCustomerNumber(),
                dto.getBookCode(),
                dto.getLoanDate(),
                dueDate,
                dto.getReturnDate(),
                dto.getNote()
        );
        var book = bookRepository.findById(dto.getBookCode()).get();
        book.setIsBorrowed(true);
        bookRepository.save(book);
        return loanRepository.save(entity);
    }

    public Boolean delete(Long id){
        Boolean haveReturnedBook = loanRepository.findById(id).get().getReturnDate() != null;
        if (haveReturnedBook){
            loanRepository.deleteById(id);
        }
        return haveReturnedBook;
    }

    public void returnBook(Long id){
        var entity = loanRepository.findById(id).get();
        entity.setReturnDate(LocalDate.now());
        var book = bookRepository.findById(entity.getBookCode()).get();
        book.setIsBorrowed(false);
        bookRepository.save(book);
    }


    public List<DropDownDTO> getCustomerDropdown(){
        return customerRepository.getDropDown();
    }

    public List<DropDownDTO> getBookDropdown(){
        return bookRepository.getDropdown();
    }
}
