package tr.edu.yildiz.yazilimkalite.librarymanagement.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum BorrowingStatus {
    @JsonProperty("İade Edilmedi")
    NOT_RETURNED("İade Edilmedi"), 
    
    @JsonProperty("İade") 
    RETURNED("İade"), 
    
    @JsonProperty("Geç İade")
    LATE("Geç İade"), 
    
    @JsonProperty("Kayıp")  
    LOST("Kayıp");

    private String name;

    BorrowingStatus() {
        name = "";
    }

	BorrowingStatus(String name) {
		this.name = name;
	}

    public String getName() {
        return name;
    }
}