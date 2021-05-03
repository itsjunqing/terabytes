package entity;

/**
 * A QualificationTitle enum class representing the types of qualifications.
 */
public enum QualificationTitle {
    CERTIFICATE("Certificate"),
    DIPLOMA("Diploma"),
    BACHELOR("Bachelor"),
    MASTERS("Masters"),
    PHD("Phd");

    private String name;

    QualificationTitle(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
