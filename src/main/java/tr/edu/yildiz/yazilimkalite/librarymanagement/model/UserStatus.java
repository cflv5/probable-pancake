package tr.edu.yildiz.yazilimkalite.librarymanagement.model;

public enum UserStatus {
    ACTIVE("Aktif"), PASSIVE("Pasif"), DELETED("Silinmiş");

    private final String name;

    UserStatus() {
        name = "";
    }

    UserStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
