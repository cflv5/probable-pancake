package tr.edu.yildiz.yazilimkalite.librarymanagement.dto;

import javax.validation.constraints.NotBlank;

import tr.edu.yildiz.yazilimkalite.librarymanagement.model.Writer;

public class WriterDto {
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    public WriterDto() {
        super();
    }
    public static WriterDto of(Writer writer) {
        return new WriterDto().id(writer.getId()).name(writer.getName()).surname(writer.getSurname());
    }

    public WriterDto id(Long id) {
        this.id = id;
        return this;
    }

    public WriterDto name(String name) {
        this.name = name;
        return this;
    }

    public WriterDto surname(String surname) {
        this.surname = surname;
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

    @Override
    public String toString() {
        return "WriterDto [id=" + id + ", name=" + name + ", surname=" + surname + "]";
    }

}
