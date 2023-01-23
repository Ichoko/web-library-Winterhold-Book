package com.winterhold.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="Author")
@Getter@Setter
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Id")
    private Long id;

    @Column(name="Title")
    private String title;

    @Column(name="FirstName")
    private String firstName;

    @Column(name="lastName")
    private String lastName;

    @Column(name="BirthDate")
    private LocalDate birthDate;

    @Column(name="DeceasedDate")
    private LocalDate deceasedDate;

    @Column(name="Education")
    private String education;

    @Column(name="Summary")
    private String summary;

}

