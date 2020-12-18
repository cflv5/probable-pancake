package tr.edu.yildiz.yazilimkalite.librarymanagement.model;

public enum CoverFormat {
    SOFT("Ciltsiz"), HARD("Ciltli");

    private String name;

    CoverFormat() {
        name = "";
    }

    CoverFormat(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
