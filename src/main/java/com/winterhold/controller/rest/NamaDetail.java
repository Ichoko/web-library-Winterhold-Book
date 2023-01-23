package com.winterhold.controller.rest;

import com.winterhold.dto.SayaDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.Month;

@CrossOrigin
@RestController
@RequestMapping("/api/saya")
public class NamaDetail {
    @GetMapping
    public ResponseEntity<Object> get (){
        var sayaNew = new SayaDTO(
                "Richo",LocalDate.of(1999, Month.AUGUST,22),
                "Unand"
        );
        return ResponseEntity.status(HttpStatus.OK).body(sayaNew);
    }
}
