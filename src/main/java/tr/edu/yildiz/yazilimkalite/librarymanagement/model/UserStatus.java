package tr.edu.yildiz.yazilimkalite.librarymanagement.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum UserStatus {
    @JsonProperty("Aktif")
    ACTIVE("Aktif"),

    @JsonProperty("Pasif")
    PASSIVE("Pasif"),

    @JsonProperty("Silinmiş")
    DELETED("Silinmiş");

    private final String name;

    UserStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
