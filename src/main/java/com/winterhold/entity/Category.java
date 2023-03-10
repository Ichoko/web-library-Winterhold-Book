package com.winterhold.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="Category")
@Getter@Setter
public class Category {
    @Id
    @Column(name="Name")
    private String name;

    @Column(name="Floor")
    private Integer floor;

    @Column(name="Isle")
    private String isle;

    @Column(name="Bay")
    private String bay;

}

