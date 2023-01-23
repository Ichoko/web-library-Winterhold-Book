package com.winterhold.dao;

import com.winterhold.dto.DropDownDTO;
import com.winterhold.dto.book.*;
import com.winterhold.dto.loan.LoanBookDetail;
import com.winterhold.dto.loan.LoanDetailDTO;
import com.winterhold.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, String> {
//
    @Query("""
            SELECT new com.winterhold.dto.book.BookGridDTO(
                bo.code,
                bo.title,
                CONCAT(aut.title, ' ', aut.firstName, ' ', aut.lastName),
                CASE WHEN bo.isBorrowed = 1 THEN 'Borrowed' ELSE 'Available' END,
                bo.releaseDate,
                bo.totalPage
            )
            FROM Book AS bo
                JOIN bo.author AS aut
            WHERE bo.categoryName = :category AND
                CONCAT(aut.firstName, ' ', aut.lastName) LIKE %:author% AND
                bo.title LIKE %:title%
            """)
    public Page<BookGridDTO> findByAll(@Param("category") String category,
                                       @Param("author") String authorName,
                                       @Param("title") String title,
                                       Pageable pageable);


//    @Query("""
//            SELECT new com.winterhold.dto.book.BooksDetailDTO(
//            bo.code,
//            bo.title,
//            bo.categoryName,
//
//            )
//            FROM Book AS bo
//                JOIN
//            """)
//    public Page<BooksDetailDTO> findByBookCode(String code, Pageable pageable)
//>> book authors
    @Query("""
            SELECT new com.winterhold.dto.book.BookDetailGridDTO(
                bo.code,
                bo.title,
                bo.authorId,
                CASE WHEN bo.isBorrowed = 1 THEN 'Borrowed' ELSE 'Available' END,
                bo.releaseDate,
                bo.totalPage,
                bo.categoryName
            )
            FROM Book AS bo
            WHERE bo.authorId = :authorId
            """)
    public Page<BookDetailGridDTO> findByAuthor(@Param("authorId") Long authorId, Pageable pageable);

//    cari buku by category

    @Query("""
            SELECT new com.winterhold.dto.book.BookCategoryDTO(
                    book.code,
                    book.title, 
                    Concat(aut.title, ' ', aut.firstName, ' ', aut.lastName),
                    CASE WHEN book.isBorrowed = 1 THEN 'Borrowed' ELSE 'Available' END, 
                    book.releaseDate, 
                    book.totalPage)
            FROM Book AS book
            LEFT JOIN book.author as aut
            LEFT JOIN book.category as cat
            Where book.title Like %:title% AND
            CONCAT(aut.title, ' ', aut.firstName, ' ', aut.lastName) like %:author% AND
            cat.name = :categoryName
            """)
    Page<BookCategoryDTO> findBookCategory(@Param("title") String title,
                                           @Param("author") String author,
                                           @Param("categoryName") String categoryName,
                                           Pageable pageable);



    //>> hitung jumlah buku (validasi)
    @Query("""
            SELECT COUNT(bo.code)
            FROM Book AS bo
            WHERE bo.code = :code
            """)
    public Long countById(@Param("code") String code);

//>> validasi(FK masih ada category)

    @Query("""
            SELECT COUNT(cat.name)
            FROM Book AS bo
                JOIN bo.category AS cat
            WHERE cat.name = :name
            """)
    public Long countByCategory(@Param("name") String categoryName);



//>> validasi(FK Author)
    @Query("""
            SELECT COUNT(aut.id)
            FROM Book AS bo
                JOIN bo.author AS aut
            WHERE aut.id = :id
            """)
    public Long countByAuthor(@Param("id") Long id);

//>> menghitung jumlah kategori yg ada bukunya
    @Query("""
            SELECT COUNT(bo.categoryName)
            FROM Book AS bo
            WHERE bo.categoryName = :category
            """)
    public Integer countTotalBookByCategory(@Param("category") String category);

    @Query("""
            SELECT new com.winterhold.dto.DropDownDTO(bo.code, bo.title)
            FROM Book AS bo
            WHERE bo.isBorrowed = 0
            """)
    public List<DropDownDTO> getDropdown();

    @Query("""
            SELECT new com.winterhold.dto.loan.LoanDetailDTO(
                bo.title,
                bo.categoryName,
                CONCAT(aut.title, ' ', aut.firstName, ' ',aut.lastName),
                cat.floor,
                cat.isle,
                cat.bay
            )
            FROM Book AS bo
                JOIN bo.category AS cat
                JOIN bo.author AS aut
            WHERE bo.code = :code
            """)
    public LoanDetailDTO getLoanBook(@Param("code") String bookCode);

    @Query("""
            SELECT new com.winterhold.dto.loan.LoanBookDetail(
                bo.code,
                bo.title,
                cat.name,
                CONCAT(aut.title, ' ', aut.firstName, ' ',aut.lastName),
                cat.floor,
                cat.isle,
                cat.bay
            )
            FROM Book AS bo
                JOIN bo.category AS cat
                JOIN bo.author AS aut
            WHERE bo.code = :code
            """)
    public LoanBookDetail loanBook(@Param("code") String bookCode);

}
