package tr.edu.yildiz.yazilimkalite.librarymanagement.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import tr.edu.yildiz.yazilimkalite.librarymanagement.dto.WriterDto;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id")
public class Writer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @ManyToMany(mappedBy = "writers")
    private List<Book> books;

    public Writer() {
        super();
    }

    public Writer id(Long id) {
        this.id = id;
        return this;
    }

    public Writer name(String name) {
        this.name = name;
        return this;
    }

    public Writer surname(String surname) {
        this.surname = surname;
        return this;
    }

    public Writer books(List<Book> books) {
        this.books = books;
        return this;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public List<Book> getBooks() {
        return this.books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "Writer [books=" + books + ", id=" + id + ", name=" + name + ", surname=" + surname + "]";
    }

    public static Writer of(WriterDto writerDto) {
        return new Writer().name(writerDto.getName()).surname(writerDto.getSurname());
    }

}