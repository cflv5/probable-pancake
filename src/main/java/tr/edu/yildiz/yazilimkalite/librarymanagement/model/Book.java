package tr.edu.yildiz.yazilimkalite.librarymanagement.model;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String isbn;

    @Column
    private Date publishDate;

    @Column
    private Integer numberOfPages;

    @Column
    @Enumerated(EnumType.STRING)
    private CoverFormat coverFormat;

    private String bookAddress;

    @Column
    private String language;

    @ManyToOne
    @JsonManagedReference
    private Publisher publisher;
    
    @ElementCollection
    private List<String> bookTypes;
    
    @ManyToMany
    @JsonManagedReference
    private List<Writer> writers;

    @Column(columnDefinition = "boolean DEFAULT false")
    private Boolean borrowed;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public Integer getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(Integer numberOfPage) {
        this.numberOfPages = numberOfPage;
    }

    public CoverFormat getCoverFormat() {
        return coverFormat;
    }

    public void setCoverFormat(CoverFormat coverFormat) {
        this.coverFormat = coverFormat;
    }

    public String getBookAddress() {
        return bookAddress;
    }

    public void setBookAddress(String bookAddress) {
        this.bookAddress = bookAddress;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public List<String> getBookTypes() {
        return bookTypes;
    }

    public void setBookTypes(List<String> bookTypes) {
        this.bookTypes = bookTypes;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public List<Writer> getWriters() {
        return this.writers;
    }

    public void setWriters(List<Writer> writers) {
        this.writers = writers;
    }

    public Boolean isBorrowed() {
        return this.borrowed;
    }

    public Boolean getBorrowed() {
        return this.borrowed;
    }

    public void setBorrowed(Boolean borrowed) {
        this.borrowed = borrowed;
    }

    @Override
    public String toString() {
        return "Book [bookAddress=" + bookAddress + ", bookTypes=" + bookTypes + ", coverFormat=" + coverFormat
                + ", id=" + id + ", borrowed=" + borrowed + ", isbn=" + isbn + ", language=" + language + ", name="
                + name + ", numOfPages=" + numberOfPages + ", publishDate=" + publishDate + ", publisher=" + publisher
                + ", writers=" + writers + "]";
    }

}
