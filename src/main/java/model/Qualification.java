package model;

public enum Qualification {
    CERTIFICATE("Certificate"),
    DIPLOMA("Diploma"),
    BACHELOR("Bachelor"),
    MASTERS("Masters"),
    PHD("Phd");

    private String name;

    Qualification(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
