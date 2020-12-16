package tr.edu.yildiz.yazilimkalite.librarymanagement.dto;

import javax.validation.constraints.NotBlank;

public class PublisherDto {
    private Long id;

    @NotBlank
    private String name;

    public PublisherDto() {
        super();
    }

    public PublisherDto id(Long id) {
        this.id = id;
        return this;
    }

    public PublisherDto name(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "PublisherDto [id=" + id + ", name=" + name + "]";
    }

}
