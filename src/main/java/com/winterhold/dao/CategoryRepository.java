package com.winterhold.dao;

import com.winterhold.dto.category.CategoryGridDTO;
import com.winterhold.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Locale;

public interface CategoryRepository extends JpaRepository<Category, String> {
    @Query("""
            SELECT new com.winterhold.dto.category.CategoryGridDTO(cat.name,cat.floor, cat.isle, cat.bay)
            FROM Category AS cat
            WHERE cat.name LIKE %:cariName%
            """)
    public List<CategoryGridDTO> findByName(@Param("cariName") String searchName, Pageable pageable);
}
