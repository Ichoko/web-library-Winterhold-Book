//package com.winterhold.validator;
//
//import com.winterhold.dto.category.UpsertCategoryDTO;
//import com.winterhold.service.CategoryService;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import javax.validation.ConstraintValidator;
//import javax.validation.ConstraintValidatorContext;
//
//public class UniqueCategoryNameValidator implements ConstraintValidator<UniqueCategoryName, UpsertCategoryDTO> {
//
//    @Autowired
//    CategoryService categoryService;
//
//    @Override
//    public void initialize(UniqueCategoryName constraintAnnotation) {
//        ConstraintValidator.super.initialize(constraintAnnotation);
//    }
//
//    @Override
//    public boolean isValid(UpsertCategoryDTO value, ConstraintValidatorContext constraintValidatorContext) {
//        String  id = value.getName();
//        id = (id == null) ? "" : id ;
//        return categoryService.isValidName(id);
//    }
//}
