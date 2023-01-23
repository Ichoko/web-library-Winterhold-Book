package com.winterhold.controller.mvc;

import com.winterhold.dto.author.AuthorDetailGridDTO;
import com.winterhold.dto.author.AuthorGridDTO;
import com.winterhold.dto.author.UpsertAuthorDTO;
import com.winterhold.dto.book.BookDetailGridDTO;
import com.winterhold.service.AuthorService;
import com.winterhold.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    private AuthorService authorService;
    @Autowired
    private BookService bookService;
    @GetMapping("/index")
    public String index(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "") String fullName,
            Model model){

        Page<AuthorGridDTO> grid = authorService.getAuthorGrid(page, fullName);
        var totalPages = grid.getTotalPages();
        model.addAttribute("grid", grid);
        model.addAttribute("currentPage", page);
        model.addAttribute("fullName", fullName);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("breadCrumbs", "Author Index");

        return "author/author-index";
    }

    @GetMapping("/upsertForm")
    public String upsertForm(@RequestParam(required = false) Long id,Model model){
        try {
            UpsertAuthorDTO upsertAuthorDTO = new UpsertAuthorDTO();
            model.addAttribute("type","insert");
            if (id != null){
                upsertAuthorDTO = authorService.getUpdate(id);
                model.addAttribute("type","update");

            }
            model.addAttribute("author",upsertAuthorDTO);
            return "author/upsert-author";
        }catch (Exception exception){
            return "redirect:/error/serverError";
        }
    }

    @PostMapping("/insert")
    public String insert(@Valid @ModelAttribute("author") UpsertAuthorDTO dto,
                         BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            if (dto.getId() != null) {
                model.addAttribute("type", "update");
                model.addAttribute("breadCrumbs", "Author Index / Update Author");
            } else {
                model.addAttribute("type", "insert");
                model.addAttribute("breadCrumbs", "Author Index / Insert Author");
            }
            return "author/upsert-author";
        } else {
            authorService.saveAuthor(dto);
            return "redirect:/author/index";
        }
    }

    @PostMapping("/update")
    public String update(
            @ModelAttribute("author") UpsertAuthorDTO dto){
        authorService.saveAuthor(dto);
        return "redirect:/author/index";
    }

    @GetMapping("/delete")
    public String  delete(Long id) {
        try {
            authorService.delete(id);
            return "redirect:/author/index";
        }catch ( Exception exception){
            return "redirect:/error/serverError";
        }
    }
    @GetMapping("/books")
    public String detail(@RequestParam(required = true) Long id,
                         @RequestParam(defaultValue = "1") Integer page,
                         @RequestParam(defaultValue = "") String name,
                         Model model){

        List<AuthorDetailGridDTO> authorDetail = authorService.getAuthorDetail(id, page, name);
        Page<BookDetailGridDTO> bookDetail = bookService.getBookDetail(id, page);
        long totalPages = authorService.getDetailTotalPages(id, name);

        model.addAttribute("authorDetail", authorDetail);
        model.addAttribute("bookDetail", bookDetail);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("name", name);

        return "author/author-detail";

    }
}
