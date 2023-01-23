package com.winterhold.controller.rest;

import com.winterhold.dto.ErrorDTO;
import com.winterhold.dto.book.InsertBookDTO;
import com.winterhold.dto.book.UpdateBookDTO;
import com.winterhold.service.BookService;
import com.winterhold.utility.MapperHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

@CrossOrigin
@RestController
@RequestMapping("/api/book")
public class BookRestController {
    @Autowired
    private BookService service;

    @GetMapping
    public ResponseEntity<Object> get(@RequestParam(defaultValue = "1") Integer page,
                                      @RequestParam(required = false) String categoryName,
                                      @RequestParam(defaultValue = "") String authorName,
                                      @RequestParam(defaultValue = "") String title){
        try {
            var grid = service.getBookGrid(page, categoryName, authorName, title);
            return ResponseEntity.status(200).body(grid);
        } catch (Exception exception) {
            var errorObject = new ErrorDTO(
                    exception.getCause().getCause().toString(),
                    exception.getMessage(),
                    LocalDateTime.now()
            );
            return ResponseEntity.status(500).body(errorObject);
        }
    }

    @GetMapping("/category")
    public ResponseEntity<Object> get(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "") String name){
        try {
            var grid = service.getCategoryGrid(page, name);
            return ResponseEntity.status(200).body(grid);
        } catch (Exception exception) {
            var errorObject = new ErrorDTO(
                    exception.getCause().getCause().toString(),
                    exception.getMessage(),
                    LocalDateTime.now()
            );
            return ResponseEntity.status(500).body(errorObject);
        }
    }

//>> akan memanggil page as default dan page sesuai filter kategori name
    @GetMapping(value={
            "category/pages",
            "category/pages/categoryName={categoryName}"
    })
    public ResponseEntity<Object> getTotalPage(@PathVariable(required = false) String categoryName){
        categoryName = (categoryName == null) ? "" : categoryName;
        try{
            Long totalPages = service.getTotalPages(categoryName);
            return ResponseEntity.status(200).body(totalPages);
        } catch (Exception exception){
            return ResponseEntity.status(500).body("There is a run-time error on the server.");
        }
    }

    @GetMapping("/{code}")
    public ResponseEntity<Object> get(@PathVariable(required = true) String code){
        try{
            var selected = service.getOneBook(code);
            return ResponseEntity.status(200).body(selected);
        } catch (Exception exception){
            var errorObject = new ErrorDTO(
                    exception.getCause().getCause().toString(),
                    exception.getMessage(),
                    LocalDateTime.now()
            );
            return ResponseEntity.status(500).body(errorObject);
        }
    }

    @PostMapping
    public ResponseEntity<Object> post(@Valid @RequestBody InsertBookDTO dto, BindingResult bindingResult){
        try{
            if (!bindingResult.hasErrors()){
                var hasilEntity = service.insertBook(dto);
                return ResponseEntity.status(201).body(hasilEntity);
            }
            var validationErrors = bindingResult.getAllErrors();
            var formattedError = MapperHelper.getErrors(validationErrors);
            return ResponseEntity.status(422).body(formattedError);
        } catch (Exception exception){
            var errorObject = new ErrorDTO(
                    exception.getCause().getCause().toString(),
                    exception.getMessage(),
                    LocalDateTime.now()
            );
            return ResponseEntity.status(500).body(errorObject);
        }
    }

    @PutMapping
    public ResponseEntity<Object> put(@Valid @RequestBody UpdateBookDTO dto, BindingResult bindingResult){
        try{
            if (!bindingResult.hasErrors()){
                var hasilEntity = service.updateBook(dto);
                return ResponseEntity.status(200).body(hasilEntity);
            }
            var validationErrors = bindingResult.getAllErrors();
            var formattedError = MapperHelper.getErrors(validationErrors);
            return ResponseEntity.status(422).body(formattedError);
        } catch (Exception exception){
            var errorObject = new ErrorDTO(
                    exception.getCause().getCause().toString(),
                    exception.getMessage(),
                    LocalDateTime.now()
            );
            return ResponseEntity.status(500).body(errorObject);
        }
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Object> delete(@PathVariable(required = true) String code){
        try{
            var dependencies = service.delete(code);
            if(dependencies == 0){
                return ResponseEntity.status(204).body(null);
            }
            return ResponseEntity.status(500).body(String.format("Tidak bisa hapus ada %s buku yang related", dependencies));
        } catch (Exception exception){
            var errorObject = new ErrorDTO(
                    exception.getCause().getCause().toString(),
                    exception.getMessage(),
                    LocalDateTime.now()
            );
            return ResponseEntity.status(500).body(errorObject);
        }
    }

    @GetMapping("/authorDropdown")
    public ResponseEntity<Object> getAuthorDropdown(){
        try{
            var grid = service.getAuthorDropdown();
            return ResponseEntity.status(200).body(grid);
        } catch (Exception exception){
            var cause = exception.getCause().getCause().toString();
            var errorObject = new ErrorDTO(
                    cause,
                    exception.getMessage(),
                    LocalDateTime.now()
            );
            return ResponseEntity.status(500).body(errorObject);
        }
    }
}
