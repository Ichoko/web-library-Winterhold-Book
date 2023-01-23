package com.winterhold.controller.rest;

import com.winterhold.dto.customer.InsertCustomerDTO;
import com.winterhold.dto.ErrorDTO;
import com.winterhold.service.CustomerService;
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
@RequestMapping("/api/customer")
public class CustomerRestController {
    @Autowired
    private CustomerService customerService;
    @GetMapping
    public ResponseEntity<Object> get(@RequestParam(defaultValue = "1") Integer page,
                                      @RequestParam(defaultValue = "") String membershipNumber,
                                      @RequestParam(defaultValue = "") String fullName)
    {
        try{
            var grid = customerService.getCustomerGrid(page,membershipNumber,fullName);
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

    @GetMapping("/{membershipNumber}")
    public ResponseEntity<Object> get(@PathVariable(required = true) String membershipNumber){
        var selected = customerService.getOneCustomer(membershipNumber);
        return ResponseEntity.status(200).body(selected);
    }

    @PostMapping
    public ResponseEntity<Object> post (@Valid @RequestBody InsertCustomerDTO dto, BindingResult bindingResult){
        if(!bindingResult.hasErrors()){
            var hasilEntity = customerService.saveCustomer(dto);
            return ResponseEntity.status(201).body(hasilEntity);
        }

        var validationErrors = bindingResult.getAllErrors();
        var formatedErrors = MapperHelper.getErrors(validationErrors);
        return ResponseEntity.status(422).body(formatedErrors);
    }

    @PutMapping
    public ResponseEntity<Object> put (@Valid @RequestBody InsertCustomerDTO dto, BindingResult bindingResult){
        if(!bindingResult.hasErrors()){
            var hasilEntity = customerService.saveCustomer(dto);
            return ResponseEntity.status(201).body(hasilEntity);
        }

        var validationErrors = bindingResult.getAllErrors();
        var formatedErrors = MapperHelper.getErrors(validationErrors);
        return ResponseEntity.status(422).body(formatedErrors);
    }
    @DeleteMapping("/{name}")
    public ResponseEntity<Object> delete (@PathVariable(required = true) String name) {
        customerService.delete(name);
        return ResponseEntity.status(HttpStatus.OK).body(name + " berhasil dihapus");
    }

}
