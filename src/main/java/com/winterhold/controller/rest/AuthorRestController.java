package com.winterhold.controller.rest;

import com.winterhold.dto.author.UpsertAuthorDTO;
import com.winterhold.dto.ErrorDTO;
import com.winterhold.service.AuthorService;
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
@RequestMapping("/api/author")
public class AuthorRestController {

    @Autowired
    private AuthorService authorService;

    @GetMapping
    public ResponseEntity<Object> get(@RequestParam(defaultValue = "1") Integer page,
                                      @RequestParam(defaultValue = "") String name)
    {
        try{
            var grid = authorService.getAuthorGrid(page,name);
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
    @GetMapping("/{id}")
    public ResponseEntity<Object> get(@PathVariable(required = true) Long id){
        var selected = authorService.getOneAuthor(id);
        return ResponseEntity.status(200).body(selected);
    }
    @PostMapping
    public ResponseEntity<Object> post (@Valid @RequestBody UpsertAuthorDTO dto, BindingResult bindingResult){
        if(!bindingResult.hasErrors()){
            var hasilEntity = authorService.saveAuthor(dto);
            return ResponseEntity.status(201).body(hasilEntity);
        }

        var validationErrors = bindingResult.getAllErrors();
        var formatedErrors = MapperHelper.getErrors(validationErrors);
        return ResponseEntity.status(422).body(formatedErrors);
    }
    @PutMapping
    public ResponseEntity<Object> put (@Valid @RequestBody UpsertAuthorDTO dto, BindingResult bindingResult){
        if(!bindingResult.hasErrors()){
            var hasilEntity = authorService.saveAuthor(dto);
            return ResponseEntity.status(201).body(hasilEntity);
        }

        var validationErrors = bindingResult.getAllErrors();
        var formatedErrors = MapperHelper.getErrors(validationErrors);
        return ResponseEntity.status(422).body(formatedErrors);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete (@PathVariable(required = true) Long id) {
        authorService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("id "+ id + " berhasil dihapus");
    }
}
