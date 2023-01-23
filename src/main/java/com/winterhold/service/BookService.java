package com.winterhold.service;

import com.winterhold.dao.AuthorRepository;
import com.winterhold.dao.BookRepository;
import com.winterhold.dao.CategoryRepository;
import com.winterhold.dto.DropDownDTO;
import com.winterhold.dto.book.*;
import com.winterhold.dto.category.CategoryGridDTO;
import com.winterhold.dto.category.InsertCategoryDTO;
import com.winterhold.dto.category.UpdateCategoryDTO;
import com.winterhold.entity.Book;
import com.winterhold.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private CategoryRepository categoryRepository;

//    find book by codeBook in REST API
    public Page<BookGridDTO> getBookGrid(Integer pageNumber, String categoryName, String authorName, String title){
        var pageable = PageRequest.of(pageNumber - 1, 10, Sort.by("code"));
        var hasilGrid = bookRepository.findByAll(categoryName, authorName, title, pageable);
        return hasilGrid;
    }
//    find book by authorId
    public Page<BookDetailGridDTO> getBookDetail(Long id, Integer page) {
        Pageable pageable = PageRequest.of(page -1, 10, Sort.by("id"));
        var hasilGrid =bookRepository.findByAuthor(id,pageable);
        return hasilGrid;
    }
//    find book by category
    public Page<BookCategoryDTO> getBooksGrid(Integer page, String title, String author, String categoryName) {
        Pageable pageable = PageRequest.of(page -1, 10, Sort.by("id"));
        Page<BookCategoryDTO> grid = bookRepository.findBookCategory(title, author,categoryName,pageable);
        return grid;
    }

// untuk menghitung jumlah book, dilakukan perulangan dalam mengitung book. jadi total page dicari manual
    public List<CategoryGridDTO> getCategoryGrid(Integer pageNumber, String searchName){
        var pageable = PageRequest.of(pageNumber - 1, 10, Sort.by("name"));
        var hasilGrid = categoryRepository.findByName(searchName, pageable);
        for (CategoryGridDTO grid : hasilGrid) {
            var totalBook = bookRepository.countTotalBookByCategory(grid.getName());
            grid.setTotalBook(totalBook);
        }
        return hasilGrid;
    }

    public Long getTotalPages(String category) {
        double totalData = (double)(categoryRepository.count());
        long totalPage = (long)(Math.ceil(totalData / 10));
        return totalPage;
    }

    public CategoryGridDTO getHeader(String categoryName){
        var entity = categoryRepository.findById(categoryName).get();
        var dto = new CategoryGridDTO(
                entity.getName(),
                entity.getFloor(),
                entity.getIsle(),
                entity.getBay()
        );
        return dto;
    }
    public Category saveCategory(InsertCategoryDTO dto) {
        Category entity = new Category(
                dto.getName(),
                dto.getFloor(),
                dto.getIsle(),
                dto.getBay()
        );
        Category entityBaru = categoryRepository.save(entity);
        return entityBaru;
    }
    public Category updateCategory(UpdateCategoryDTO dto) {
        Category entity = new Category(
                dto.getName(),
                dto.getFloor(),
                dto.getIsle(),
                dto.getBay()
        );
        Category entityBaru = categoryRepository.save(entity);
        return entityBaru;
    }

    public UpdateCategoryDTO getUpdateCategory(String name) {
        Optional<Category> nullableEntity = categoryRepository.findById(name);
        Category entity = nullableEntity.get();

        UpdateCategoryDTO categoryDTO = new UpdateCategoryDTO(
                entity.getName(),
                entity.getFloor(),
                entity.getIsle(),
                entity.getBay()
        );

        return categoryDTO;
    }
    public SingleBookDTO getOneBook(String code){
        Book entity = bookRepository.findById(code).get();
        var dto = new SingleBookDTO(
                entity.getCode(),
                entity.getTitle(),
                entity.getCategoryName(),
                entity.getAuthorId(),
                entity.getIsBorrowed(),
                entity.getSummary(),
                entity.getReleaseDate(),
                entity.getTotalPage()
        );
        return dto;
    }

    public Book insertBook(InsertBookDTO dto){
        Book entity = new Book(
                dto.getCode(),
                dto.getTitle(),
                dto.getCategoryName(),
                dto.getAuthorId(),
                false,
                dto.getSummary(),
                dto.getReleaseDate(),
                dto.getTotalPage()
        );
        return bookRepository.save(entity);
    }

    public Book updateBook(UpdateBookDTO dto){
        Book entity = new Book(
                dto.getCode(),
                dto.getTitle(),
                dto.getCategoryName(),
                dto.getAuthorId(),
                dto.getIsBorrowed(),
                dto.getSummary(),
                dto.getReleaseDate(),
                dto.getTotalPage()
        );
        return bookRepository.save(entity);
    }
//>>
    public UpdateBookDTO getUpdate(String code){
        Book entity = bookRepository.findById(code).get();
        UpdateBookDTO dto = new UpdateBookDTO(
                entity.getCode(),
                entity.getTitle(),
                entity.getCategoryName(),
                entity.getAuthorId(),
                entity.getIsBorrowed(),
                entity.getSummary(),
                entity.getReleaseDate(),
                entity.getTotalPage()
        );
        return dto;
    }

    public Boolean isValidCode(String code){
        var totalCategory = bookRepository.countById(code);
        return totalCategory == 0;
    }

//    delete category
    public void deleteCategory (String name){
    categoryRepository.deleteById(name);
}
//    delete Book
    public Long delete(String id){
        Long totalCategory = bookRepository.countByCategory(id);
        if (totalCategory <= 0){
        bookRepository.deleteById(id);
        }
        return totalCategory;
    }

    public List<DropDownDTO> getAuthorDropdown(){
        return authorRepository.getDropdownAuthor();
    }


    public CategoryGridDTO getOneCategory(String bookCode){
        Category entity = categoryRepository.findById(bookCode).get();
        var dto = new CategoryGridDTO(
                entity.getName(),
                entity.getFloor(),
                entity.getIsle(),
                entity.getBay()
        );
        return dto;
    }

}
