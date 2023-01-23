package com.winterhold.service;

import com.winterhold.dao.CategoryRepository;
import com.winterhold.dto.category.CategoryGridDTO;
import com.winterhold.dto.category.UpsertCategoryDTO;
import com.winterhold.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryGridDTO> getCategoryGrid(Integer pageNumber, String searchName){
        var pageable = PageRequest.of(pageNumber -1,10, Sort.by("name"));
        var  hasilGrid = categoryRepository.findByName(searchName,pageable);
        return hasilGrid;
    }

    public Category saveCategory(UpsertCategoryDTO dto) {
        Category entity = new Category(
                dto.getName(),
                dto.getFloor(),
                dto.getIsle(),
                dto.getBay()
        );
        Category entityBaru = categoryRepository.save(entity);
        return entityBaru;
    }

    public CategoryGridDTO getOneCategory(String name){
        Category entity = categoryRepository.findById(name).get();
        var dto = new CategoryGridDTO(
                entity.getName(),
                entity.getFloor(),
                entity.getIsle(),
                entity.getBay()
        );
        return dto;
    }
    public void delete (String name){
        categoryRepository.deleteById(name);
    }
}
