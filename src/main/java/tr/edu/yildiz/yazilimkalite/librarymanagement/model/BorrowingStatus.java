package tr.edu.yildiz.yazilimkalite.librarymanagement.model;

public enum BorrowingStatus {
    NOT_RETURNED("İade Edilmedi"), RETURNED("İade"), LATE("Geç İade"), LOST("Kayıp");

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