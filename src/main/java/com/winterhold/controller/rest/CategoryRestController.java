package com.winterhold.controller.rest;

import com.winterhold.dto.category.UpsertCategoryDTO;
import com.winterhold.dto.ErrorDTO;
import com.winterhold.service.CategoryService;
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
@RequestMapping("/api/category")
public class CategoryRestController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<Object> get(@RequestParam(defaultValue = "1") Integer page,
                                      @RequestParam(defaultValue = "") String name)
    {
        try{
            var grid = categoryService.getCategoryGrid(page,name);
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
    @GetMapping("/{name}")
    public ResponseEntity<Object> get(@PathVariable(required = true) String name){
        var selected = categoryService.getOneCategory(name);
        return ResponseEntity.status(200).body(selected);
    }
    @PostMapping
    public ResponseEntity<Object> post (@Valid @RequestBody UpsertCategoryDTO dto, BindingResult bindingResult){
        if(!bindingResult.hasErrors()){
            var hasilEntity = categoryService.saveCategory(dto);
            return ResponseEntity.status(201).body(hasilEntity);
        }

        var validationErrors = bindingResult.getAllErrors();
        var formatedErrors = MapperHelper.getErrors(validationErrors);
        return ResponseEntity.status(422).body(formatedErrors);
    }
    @PutMapping
    public ResponseEntity<Object> put (@Valid @RequestBody UpsertCategoryDTO dto, BindingResult bindingResult){
        if(!bindingResult.hasErrors()){
            var hasilEntity = categoryService.saveCategory(dto);
            return ResponseEntity.status(201).body(hasilEntity);
        }

        var validationErrors = bindingResult.getAllErrors();
        var formatedErrors = MapperHelper.getErrors(validationErrors);
        return ResponseEntity.status(422).body(formatedErrors);
    }
    @DeleteMapping("/{name}")
    public ResponseEntity<Object> delete (@PathVariable(required = true) String name) {
        categoryService.delete(name);
        return ResponseEntity.status(HttpStatus.OK).body(name + " berhasil dihapus");
    }
}
