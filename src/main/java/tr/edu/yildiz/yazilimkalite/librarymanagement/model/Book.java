package tr.edu.yildiz.yazilimkalite.librarymanagement.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

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
    private Timestamp publishDate;

    @Column
    private Integer numOfPage;

    @Column
    @Enumerated(EnumType.STRING)
    private CoverFormat coverFormat;

    @OneToOne
    private BookAddress bookAdress;

    @Column
    private String language;

    @OneToOne
    private Publisher publisher;

    @OneToOne
    private BookType bookType;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "book_id")
    private List<BookTag> tags;

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

    public Timestamp getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Timestamp publishDate) {
        this.publishDate = publishDate;
    }

    public Integer getNumOfPage() {
        return numOfPage;
    }

    public void setNumOfPage(Integer numOfPage) {
        this.numOfPage = numOfPage;
    }

    public CoverFormat getCoverFormat() {
        return coverFormat;
    }

    public void setCoverFormat(CoverFormat coverFormat) {
        this.coverFormat = coverFormat;
    }

    public BookAddress getBookAdress() {
        return bookAdress;
    }

    public void setBookAdress(BookAddress bookAdress) {
        this.bookAdress = bookAdress;
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

    public BookType getBookType() {
        return bookType;
    }

    public void setBookType(BookType bookType) {
        this.bookType = bookType;
    }

    public List<BookTag> getTags() {
        return tags;
    }

    public void setTags(List<BookTag> tags) {
        this.tags = tags;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

}
