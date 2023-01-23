package com.winterhold.service;

import com.winterhold.dao.AuthorRepository;
import com.winterhold.dto.author.AuthorDetailGridDTO;
import com.winterhold.dto.author.AuthorGridDTO;
import com.winterhold.dto.author.OneAuthor;
import com.winterhold.dto.author.UpsertAuthorDTO;
import com.winterhold.entity.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    public Page<AuthorGridDTO> getAuthorGrid(Integer pageNumber, String searchName){
        var pageable = PageRequest.of(pageNumber -1,10, Sort.by("id"));
        var  hasilGrid = authorRepository.findByName(searchName,pageable);
        return hasilGrid;
    }

    public List<AuthorDetailGridDTO> getAuthorDetail(Long id, Integer page, String name) {
        Pageable pagination = PageRequest.of(page - 1, 10, Sort.by("id"));
        List<AuthorDetailGridDTO> grid = authorRepository.findDetail(id, name, pagination);
        return grid;
    }
    public Author saveAuthor(UpsertAuthorDTO dto) {
        Author entity = new Author(
                dto.getId(),
                dto.getTitle(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getBirthDate(),
                dto.getDeceasedDate(),
                dto.getEducation(),
                dto.getSummary()
        );
        Author entityBaru = authorRepository.save(entity);
        return entityBaru;
    }

    public UpsertAuthorDTO getUpdate(Long id){
        var author = authorRepository.findById(id).get();
        UpsertAuthorDTO dto = new UpsertAuthorDTO(
                author.getId(),
                author.getTitle(),
                author.getFirstName(),
                author.getLastName(),
                author.getBirthDate(),
                author.getDeceasedDate(),
                author.getEducation(),
                author.getSummary()
        );
        return dto;
    }

    public OneAuthor getOneAuthor(Long id){
        Author entity = authorRepository.findById(id).get();
        var dto = new OneAuthor(
                entity.getId(),
                entity.getTitle(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getBirthDate(),
                entity.getDeceasedDate(),
                entity.getEducation(),
                entity.getSummary()
        );
        return dto;
    }
    public Long getDetailTotalPages(Long id, String name) {
        double totalData = (double) (authorRepository.count(id, name));
        long totalPage = (long)(Math.ceil(totalData / 10));
        return totalPage;
    }

    public void delete (Long id){
        authorRepository.deleteById(id);
    }
}
