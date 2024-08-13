package com.lawlie8.gutenbergreader.entities;

import jakarta.persistence.*;

import java.time.Year;
import java.util.Date;

@Entity
@Table(name = "books")
public class Books {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "book_id")
    private Long bookId;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "book_type")
    private String bookType;

    @Column(name = "book_language")
    private String bookLanguage;

    @Column(name = "book_description")
    private String bookDescription;

    @Column(name = "year_of_publication")
    private Year yearOfPublication;

    @Column(name = "upload_date")
    private Date uploadDate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBookType() {
        return bookType;
    }

    public String getBookLanguage() {
        return bookLanguage;
    }

    public void setBookLanguage(String bookLanguage) {
        this.bookLanguage = bookLanguage;
    }

    public String getBookDescription() {
        return bookDescription;
    }

    public void setBookDescription(String bookDescription) {
        this.bookDescription = bookDescription;
    }

    public void setBookType(String bookType) {
        this.bookType = bookType;
    }

    public Year getYearOfPublication() {
        return yearOfPublication;
    }

    public void setYearOfPublication(Year yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }
}
