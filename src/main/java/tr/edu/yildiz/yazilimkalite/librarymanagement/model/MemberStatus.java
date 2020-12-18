package tr.edu.yildiz.yazilimkalite.librarymanagement.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum MemberStatus {
    @JsonProperty("Aktif")
    ACTIVE("Aktif"), 
    
    @JsonProperty("Pasif")
    PASSIVE("Pasif"), 
    
    @JsonProperty("Bekliyor")
    WAITING("Bekliyor");

    private String name;

    MemberStatus() {
        name = "";
    }

    MemberStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}