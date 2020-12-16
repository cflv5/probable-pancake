package tr.edu.yildiz.yazilimkalite.librarymanagement.dto;

import javax.validation.constraints.NotBlank;

import tr.edu.yildiz.yazilimkalite.librarymanagement.model.LibrarySettingType;

public class LibrarySettingDto {
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String value;

    @NotBlank
    private LibrarySettingType type;

    public LibrarySettingDto() {
        super();
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

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public LibrarySettingType getType() {
        return this.type;
    }

    public void setType(LibrarySettingType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "LibrarySettingDto [id=" + id + ", name=" + name + ", type=" + type + ", value=" + value + "]";
    }

}
