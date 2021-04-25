package model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// change
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

    // return enum as a list of strings
    public static List<String> getNames()
    {
        return Stream.of(QualificationTitle.values()).map(q->q.toString()).collect(Collectors.toList());
    }
}
