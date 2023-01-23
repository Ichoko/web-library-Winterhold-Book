package com.winterhold.controller.mvc;

import com.winterhold.dto.author.UpsertAuthorDTO;
import com.winterhold.dto.customer.CustomerGridDTO;
import com.winterhold.dto.customer.InsertCustomerDTO;
import com.winterhold.dto.customer.UpdateCustomerDTO;
import com.winterhold.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/customer")

public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @GetMapping("/index")
    private String index(@RequestParam(defaultValue = "1") Integer page,
                         @RequestParam (defaultValue = "") String membershipNumber,
                         @RequestParam (defaultValue = "") String name,
                         Model model){

        Page<CustomerGridDTO> grid = customerService.getCustomerGrid(page, membershipNumber, name);
        var totalPages = grid.getTotalPages();
        model.addAttribute("grid", grid);
        model.addAttribute("currentPage", page);
        model.addAttribute("membershipNumber", membershipNumber);
        model.addAttribute("name", name);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("breadCrumbs", "Customer Index");
        return "customer/customer-index";
    }

    @GetMapping("/insertForm")
    public String insertForm(@RequestParam(required = false) String membershipNumber,Model model){
        try {
            InsertCustomerDTO insertCustomerDTO = new InsertCustomerDTO();
            model.addAttribute("type","insert");
            model.addAttribute("customer", insertCustomerDTO);
            return "customer/upsert-customer";
        }catch (Exception exception){
            return "redirect:/error/serverError";
        }
    }

    @GetMapping("/updateForm")
    public String updateForm(@RequestParam(required = false) String membershipNumber,Model model) {
        try {
            UpdateCustomerDTO updateCustomerDTO = customerService.getUpdate(membershipNumber);
            model.addAttribute("customer", updateCustomerDTO);
            model.addAttribute("type", "update");
            return "customer/upsert-customer";
        } catch (Exception exception) {
            return "redirect:/error/serverError";
        }
    }

    @PostMapping("/insert")
    public String insert(@Valid @ModelAttribute("customer") InsertCustomerDTO dto,
                         BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
                model.addAttribute("type", "insert");
                model.addAttribute("breadCrumbs", "Customer Index / Insert Customer");
            return "customer/upsert-customer";
        } else {
            customerService.saveCustomer(dto);
            return "redirect:/customer/index";
        }
    }
    @PostMapping("/update")
    public String update(@ModelAttribute("customer") UpdateCustomerDTO dto,
                         BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            model.addAttribute("breadCrumbs", "Customer Index / Update Customer");
            return "customer/upsert-customer";
        }
        customerService.updateCustomer(dto);
        return "redirect:/customer/index";
    }

    @GetMapping("/delete")
    public String  delete(String membershipNumber) {
        try {
            customerService.delete(membershipNumber);
            return "redirect:/customer/index";
        }catch ( Exception exception){
            return "redirect:/error/serverError";
        }
    }
}
