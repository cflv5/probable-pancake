package tr.edu.yildiz.yazilimkalite.librarymanagement.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class LibrarySetting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, updatable = false)
    private String name;

    private String value;

    private LibrarySettingType type;

    public LibrarySetting() {
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
        return "LibrarySetting [id=" + id + ", name=" + name + ", type=" + type + ", value=" + value + "]";
    }

}
