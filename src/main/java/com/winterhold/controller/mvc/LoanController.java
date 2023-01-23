package com.winterhold.controller.mvc;

import com.winterhold.dto.loan.LoanGridDTO;
import com.winterhold.dto.loan.UpsertLoanDTO;
import com.winterhold.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/loan")
public class LoanController {

    @Autowired
    private LoanService service;

    @GetMapping("/index")
    public String index(@RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue = "") String bookTitle,
                        @RequestParam(defaultValue = "") String customerName,
                        Model model) {
        Page<LoanGridDTO> grid = service.getLoanGrid(page, bookTitle,customerName);
        var totalPages = grid.getTotalPages();
        model.addAttribute("grid", grid);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);
        model.addAttribute("customerName", customerName);
        model.addAttribute("bookTitle", bookTitle);
        return "/loan/loan-index";
    }

    @PostMapping("/insert")
    public String insert(@Valid @ModelAttribute("loan") UpsertLoanDTO dto,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            var bookDropdown = service.getBookDropdown();
            var customerDropdown = service.getCustomerDropdown();
            String actionType = "insert";
            model.addAttribute("bookDropDown", bookDropdown);
            model.addAttribute("customerDropDown", customerDropdown);
            model.addAttribute("actionType", actionType);
            return "loan/loan-insert-form";
        } else {
            service.save(dto);
            return "redirect:/loan/index";
        }
    }

    @GetMapping("/detail")
    public String detail(@RequestParam(required = true) Long id,
                         Model model){
        var loanDetail = service.loanDetail(id);
        var bookDetail = loanDetail.getBook();
        var customerDetail = loanDetail.getCustomer();

        model.addAttribute("bookDetail",bookDetail);
        model.addAttribute("customerDetail",customerDetail);
        return "loan/loan-detail";
    }

    @GetMapping("/insertForm")
    public String insertForm(@RequestParam(required = false) Long id, Model model) {
        var bookDropdown = service.getBookDropdown();
        var customerDropdown = service.getCustomerDropdown();
        UpsertLoanDTO dto = new UpsertLoanDTO();
        model.addAttribute("loan", dto);
        model.addAttribute("bookDropDown", bookDropdown);
        model.addAttribute("customerDropDown", customerDropdown);
        model.addAttribute("actionType", "insert");
        return "loan/loan-insert-form";
    }

    @GetMapping("/updateForm")
    public String updateForm(@RequestParam(required = true) Long id, Model model) {
        var bookDropdown = service.getBookDropdown();
        var customerDropdown = service.getCustomerDropdown();
        UpsertLoanDTO dto = service.getDTOForm(id);
        service.returnBook(dto.getId());
        model.addAttribute("loan", dto);
        model.addAttribute("bookDropDown", bookDropdown);
        model.addAttribute("customerDropDown", customerDropdown);
        model.addAttribute("actionType", "update");
        return "loan/loan-update-form";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("loan") UpsertLoanDTO dto,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            var bookDropdown = service.getBookDropdown();
            var customerDropdown = service.getCustomerDropdown();
            String actionType = "update";
            model.addAttribute("bookDropDown", bookDropdown);
            model.addAttribute("customerDropDown", customerDropdown);
            model.addAttribute("actionType", actionType);
            return "loan/loan-update-form";
        } else {
            service.getUpdate(dto);
            return "redirect:/loan/index";
        }
    }

    @GetMapping("/return")
    public String returnBook(@RequestParam(required = true) Long id,
                             Model model){
        service.returnBook(id);
        return "redirect:/loan/index";
    }
}
