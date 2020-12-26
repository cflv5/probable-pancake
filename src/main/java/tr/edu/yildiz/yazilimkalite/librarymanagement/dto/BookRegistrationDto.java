package tr.edu.yildiz.yazilimkalite.librarymanagement.dto;

import java.sql.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import tr.edu.yildiz.yazilimkalite.librarymanagement.model.CoverFormat;

public class BookRegistrationDto {
    private Long id;

    @NotBlank
    private String isbn;

    @NotBlank
    private String name;

    private Date publishDate;

    private Integer numberOfPages;

    private CoverFormat coverFormat;

    private String bookAddress;

    private String language;

    @NotNull
    private Long publisher;

    private List<String> bookTypes;

    @NotEmpty
    private List<Long> writers;

    public BookRegistrationDto() {
        super();
    }

    public BookRegistrationDto id(Long id) {
        this.id = id;
        return this;
    }

    public BookRegistrationDto isbn(String isbn) {
        this.isbn = isbn;
        return this;
    }

    public BookRegistrationDto name(String name) {
        this.name = name;
        return this;
    }

    public BookRegistrationDto publishDate(Date publishDate) {
        this.publishDate = publishDate;
        return this;
    }

    public BookRegistrationDto numberOfPages(Integer numberOfPages) {
        this.numberOfPages = numberOfPages;
        return this;
    }

    public BookRegistrationDto coverFormat(CoverFormat coverFormat) {
        this.coverFormat = coverFormat;
        return this;
    }

    public BookRegistrationDto bookAddress(String bookAddress) {
        this.bookAddress = bookAddress;
        return this;
    }

    public BookRegistrationDto language(String language) {
        this.language = language;
        return this;
    }

    public BookRegistrationDto publisher(Long publisher) {
        this.publisher = publisher;
        return this;
    }

    public BookRegistrationDto bookTypes(List<String> bookTypes) {
        this.bookTypes = bookTypes;
        return this;
    }

    public BookRegistrationDto writers(List<Long> writers) {
        this.writers = writers;
        return this;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsbn() {
        return this.isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getPublishDate() {
        return this.publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public Integer getNumberOfPages() {
        return this.numberOfPages;
    }

    public void setNumberOfPages(Integer numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public CoverFormat getCoverFormat() {
        return this.coverFormat;
    }

    public void setCoverFormat(CoverFormat coverFormat) {
        this.coverFormat = coverFormat;
    }

    public String getBookAddress() {
        return this.bookAddress;
    }

    public void setBookAddress(String bookAddress) {
        this.bookAddress = bookAddress;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Long getPublisher() {
        return this.publisher;
    }

    public void setPublisher(Long publisher) {
        this.publisher = publisher;
    }

    public List<String> getBookTypes() {
        return this.bookTypes;
    }

    public void setBookTypes(List<String> bookTypes) {
        this.bookTypes = bookTypes;
    }

    public List<Long> getWriters() {
        return this.writers;
    }

    public void setWriters(List<Long> writers) {
        this.writers = writers;
    }

    @Override
    public String toString() {
        return "BookRegistrationDto [bookAddress=" + bookAddress + ", bookTypes=" + bookTypes + ", coverFormat="
                + coverFormat + ", id=" + id + ", isbn=" + isbn + ", language=" + language + ", name=" + name
                + ", numberOfPages=" + numberOfPages + ", publishDate=" + publishDate + ", publisher=" + publisher
                + ", writers=" + writers + "]";
    }

}
