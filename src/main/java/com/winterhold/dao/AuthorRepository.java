package com.winterhold.dao;

import com.winterhold.dto.DropDownDTO;
import com.winterhold.dto.author.AuthorDetailGridDTO;
import com.winterhold.dto.author.AuthorGridDTO;
import com.winterhold.entity.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author,Long> {
    @Query("""
            SELECT new com.winterhold.dto.author.AuthorGridDTO(
                auth.id , CONCAT(auth.firstName,' ', auth.lastName), auth.birthDate, 
                CASE WHEN auth.deceasedDate = NULL THEN 'Alive' ELSE 'Deceased' END,
                auth.education, auth.deceasedDate)
            FROM Author AS auth
            WHERE CONCAT(auth.firstName,' ', auth.lastName) LIKE %:fullName%
            """)
    public Page<AuthorGridDTO> findByName(@Param("fullName") String fullName, Pageable pageable);

    @Query("""
            SELECT new com.winterhold.dto.author.AuthorDetailGridDTO(aut.id, 
                CONCAT(aut.title, ' ', aut.firstName, ' ', aut.lastName),
                aut.birthDate, 
                aut.deceasedDate, 
                aut.education, 
                aut.summary)
            FROM Author AS aut
            WHERE aut.id = :id
                AND CONCAT(aut.title, ' ', aut.firstName, ' ', aut.lastName) LIKE %:name%
             """)
    List<AuthorDetailGridDTO> findDetail(@Param("id") Long id, @Param("name") String name, Pageable pageable);

    @Query("""
            SELECT new com.winterhold.dto.DropDownDTO(
                aut.id ,CONCAT(aut.firstName,' ',aut.lastName))
            FROM Author as aut
            """)
    public List<DropDownDTO> getDropdownAuthor();

    @Query("""
             SELECT COUNT(aut.id)
             FROM Author AS aut
             WHERE aut.id = :id
                AND CONCAT(aut.title, ' ', aut.firstName, ' ', aut.lastName) LIKE %:name%
             """)
    public Long count(@Param("id") Long id,
                      @Param("name") String name);
}

