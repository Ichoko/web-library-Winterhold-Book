package com.winterhold.controller.mvc;

import com.winterhold.dto.DropDownDTO;
import com.winterhold.dto.ErrorDTO;
import com.winterhold.dto.book.*;
import com.winterhold.dto.category.CategoryGridDTO;
import com.winterhold.dto.category.InsertCategoryDTO;
import com.winterhold.dto.category.UpdateCategoryDTO;
import com.winterhold.service.AuthorService;
import com.winterhold.service.BookService;
import com.winterhold.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService bookService;


    //Tampilan Buku berdasarkan kategori
    @GetMapping("/index")
    public String get(@RequestParam(defaultValue = "1") Integer page,
                      @RequestParam(defaultValue = "") String categoryName,
                      Model model){
        try {
            var grid = bookService.getCategoryGrid(page, categoryName);
            var totalHalaman = bookService.getTotalPages(categoryName);
            model.addAttribute("grid",grid);
            model.addAttribute("totalPages",totalHalaman);
            model.addAttribute("currentPage", page);
            model.addAttribute("categoryName",categoryName);
            model.addAttribute("breadCrumbs", "Book Index");
            return "book/book-index";
        } catch (Exception exception) {
            return "redirect:/error/serverError";
        }
    }

    @GetMapping("/upsertForm")
    public String upsertForm(@RequestParam(required = false) String name, Model model) {
        if (name != null) {

            UpdateCategoryDTO dto = bookService.getUpdateCategory(name);

            model.addAttribute("category", dto);
            model.addAttribute("type", "update");
            model.addAttribute("breadCrumbs", "Book Index / Update Category");
        } else {

            InsertCategoryDTO dto = new InsertCategoryDTO();

            model.addAttribute("category", dto);
            model.addAttribute("type", "insert");
            model.addAttribute("breadCrumbs", "Book Index / Insert Category");
        }
        return "book/category-form";
    }
    @PostMapping("/insert")
    public String insert(@Valid @ModelAttribute("category") InsertCategoryDTO dto,
                         BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
                model.addAttribute("type", "insert");
                model.addAttribute("breadCrumbs", "Category Index / Insert Category");
            return "book/category-form";
        } else {
            bookService.saveCategory(dto);
            return "redirect:/book/index";
        }
    }

    @PostMapping("/update")
    public String update(
            @ModelAttribute("category") UpdateCategoryDTO dto,
            BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
                model.addAttribute("type", "update");
                model.addAttribute("breadCrumbs", "Category Index / Update Category");
            return "category/category-form";
        } else {
            bookService.updateCategory(dto);
            return "redirect:/book/index";
        }
    }
    @GetMapping("/delete")
    public String delete(@RequestParam(required = true) String name, Model model) {
        try {
            bookService.deleteCategory(name);
            return "redirect:/book/index";
        }catch (Exception exception) {
            return "redirect:/error/serverError";
        }

    }
    @GetMapping("/booksDetail")
    public String booksDetail(@RequestParam(defaultValue = "1") Integer page,
                              @RequestParam(defaultValue = "") String title,
                              @RequestParam(defaultValue = "") String author,
                              @RequestParam(required = true) String categoryName,
                              Model model){
        Page<BookCategoryDTO> grid = bookService.getBooksGrid(page, title,author,categoryName);
        CategoryGridDTO categoryGridDTO = bookService.getOneCategory(categoryName);
        long totalPages = bookService.getTotalPages(title);

        model.addAttribute("grid", grid);
        model.addAttribute("category", categoryGridDTO);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("author", author);
        model.addAttribute("title", title);
        model.addAttribute("currentPage", page);
        model.addAttribute("breadCrumbs", "Books Index");

        return "book/book-detail";
    }

    @GetMapping("/upsertBook")
    public String upsertFormBook(@RequestParam(required = false) String code,
                                 String categoryName,
                                 Model model) {
        List<DropDownDTO> authorDropdown = bookService.getAuthorDropdown();
        model.addAttribute("authorDropdown", authorDropdown);
        if (code == null) {
            InsertBookDTO dto = new InsertBookDTO();
            dto.setCategoryName(categoryName);
            model.addAttribute("category", dto);
            model.addAttribute("book", dto);
            model.addAttribute("type", "insertBook");
            model.addAttribute("breadCrumbs", "Books Index / Insert Books");
        } else {
            SingleBookDTO dto = bookService.getOneBook(code);
            model.addAttribute("book", dto);
            model.addAttribute("type", "updateBook");
            model.addAttribute("breadCrumbs", "Book Index / Update Book");
        }
        return "book/upsert-book";
    }


    @PostMapping("/insertBook")
    public String upsert(@Valid @ModelAttribute("book") InsertBookDTO dto,
                         BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            List<DropDownDTO> authorDropdown = bookService.getAuthorDropdown();
            model.addAttribute("authorDropdown", authorDropdown);
            model.addAttribute("type", "insertBook");
            model.addAttribute("breadCrumbs", "Book Index / Insert Book");
            return "book/upsert-book";
        } else {
            bookService.insertBook(dto);
            return "redirect:/book/booksDetail?categoryName="+dto.getCategoryName();
        }
    }

    @PostMapping("/updateBook")
    public String upsert(@Valid @ModelAttribute("book") UpdateBookDTO dto,
                         BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            List<DropDownDTO> authorDropdown = bookService.getAuthorDropdown();
            model.addAttribute("authorDropdown", authorDropdown);
            model.addAttribute("type", "updateBook");
            model.addAttribute("breadCrumbs", "Book Index / update Book");
            return "book/upsert-book";
        } else {
            bookService.updateBook(dto);
            return "redirect:/book/index";
        }
    }

    @GetMapping("/deleteBook")
    public String deleteBook(@RequestParam(required = true) String code,
                             @RequestParam(required = true)String categoryName,
                             Model model) {
        try {
            bookService.delete(code);
            return "redirect:/book/booksDetail?categoryName="+categoryName ;
        } catch (Exception exception) {
            return "redirect:/error/serverError";
        }
    }

}
