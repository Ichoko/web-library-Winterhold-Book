package com.winterhold.controller.rest;

import com.winterhold.dto.ErrorDTO;
import com.winterhold.dto.loan.UpsertLoanDTO;
import com.winterhold.service.LoanService;
import com.winterhold.utility.MapperHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

@CrossOrigin
@RestController
@RequestMapping("/api/loan")
public class LoanRestController {
    @Autowired
    private LoanService loanService;

    @GetMapping
    public ResponseEntity<Object> get(@RequestParam(defaultValue = "1") Integer page,
                                      @RequestParam(defaultValue = "") String title,
                                      @RequestParam(defaultValue = "") String customerName)
    {
        try{
            var grid = loanService.getLoanGrid(page,title,customerName);
            return ResponseEntity.status(HttpStatus.OK).body(grid);
//            return ResponseEntity.status(200).body(grid);
        }catch (Exception exception){
            var cause = exception.getCause().getCause().toString();
            var errorObject = new ErrorDTO(
                    cause,
                    exception.getMessage(),
                    LocalDateTime.now()
            );
            return ResponseEntity.status(500).body(errorObject);
        }
    }

    @PostMapping
    public ResponseEntity<Object> post (@Valid @RequestBody UpsertLoanDTO dto, BindingResult bindingResult){
        if(!bindingResult.hasErrors()){
            dto.setDueDate(dto.getLoanDate().plusDays(5));
            var hasilEntity = loanService.save(dto);
            return ResponseEntity.status(201).body(hasilEntity);
        }

        var validationErrors = bindingResult.getAllErrors();
        var formatedErrors = MapperHelper.getErrors(validationErrors);
        return ResponseEntity.status(422).body(formatedErrors);
    }
    @GetMapping("/{code}")
    public ResponseEntity<Object> get(@PathVariable(required = true) String code){
        var selected = loanService.getLoanBook(code);
        return ResponseEntity.status(200).body(selected);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Object> get(@PathVariable(required = true) Long id){
        var selected = loanService.getSingleLoan(id);
        return ResponseEntity.status(200).body(selected);
    }

    @PutMapping
    public ResponseEntity<Object> put (@Valid @RequestBody UpsertLoanDTO dto, BindingResult bindingResult){
        if(!bindingResult.hasErrors()){
            var hasilEntity = loanService.getUpdate(dto);
            return ResponseEntity.status(201).body(hasilEntity);
        }

        var validationErrors = bindingResult.getAllErrors();
        var formatedErrors = MapperHelper.getErrors(validationErrors);
        return ResponseEntity.status(422).body(formatedErrors);
    }


    @PatchMapping("/return/{id}")
    public ResponseEntity<Object> patch (@PathVariable(required = true) Long id){
        loanService.returnBook(id);
        return ResponseEntity.status(200).body("id " +id+" telah selesai dikembalikan!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete (@PathVariable(required = true) Long id) {
        loanService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(id + " berhasil dihapus");
    }

    @GetMapping("/customerDropdown")
    public ResponseEntity<Object> getCustomerDropdown(){
            var grid = loanService.getCustomerDropdown();
            return ResponseEntity.status(200).body(grid);
    }

    @GetMapping("/bookDropdown")
    public ResponseEntity<Object> getBookDropdown(){
        var grid = loanService.getBookDropdown();
        return ResponseEntity.status(200).body(grid);
    }
}
