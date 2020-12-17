package tr.edu.yildiz.yazilimkalite.librarymanagement.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

import org.hibernate.annotations.CreationTimestamp;

import tr.edu.yildiz.yazilimkalite.librarymanagement.dto.PublisherDto;

@Entity
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @CreationTimestamp
    private Timestamp createdAt;
    
    @OneToMany(mappedBy = "publisher", cascade = CascadeType.PERSIST)
    @JsonBackReference
    private List<Book> books;

    public Publisher() {
        super();
    }

    public static Publisher of(PublisherDto publisherDto) {
        return new Publisher().name(publisherDto.getName());
    }

    public Publisher id(Long id) {
        this.id = id;
        return this;
    }

    public Publisher name(String name) {
        this.name = name;
        return this;
    }

    public Publisher createdAt(Timestamp createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Publisher books(List<Book> books) {
        this.books = books;
        return this;
    }

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

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public List<Book> getBooks() {
        return this.books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "Publisher [books=" + books + ", createdAt=" + createdAt + ", id=" + id + ", name=" + name + "]";
    }

}
