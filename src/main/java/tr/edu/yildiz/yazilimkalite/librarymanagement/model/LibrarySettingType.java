package tr.edu.yildiz.yazilimkalite.librarymanagement.model;

public enum LibrarySettingType {
    NUMERIC("Nümerik"), STRING("String"), BOOLEAN("Mantıksal");

    private String name;

    private LibrarySettingType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
