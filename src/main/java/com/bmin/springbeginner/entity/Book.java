package com.bmin.springbeginner.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="book")
@Getter @Setter
public class Book {
    // fields

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @Column(name="author")
    private String author;

    @Column(name="title")
    private String title;

    @Column(name="price")
    private double price;
}
