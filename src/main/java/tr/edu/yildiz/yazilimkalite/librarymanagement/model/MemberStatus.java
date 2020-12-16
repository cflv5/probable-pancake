package tr.edu.yildiz.yazilimkalite.librarymanagement.model;

public enum MemberStatus {
    ACTIVE("Aktif"), PASSIVE("Pasif"), WAITING("Bekliyor");

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