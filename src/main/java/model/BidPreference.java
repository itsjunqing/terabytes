package model;

import lombok.Data;

@Data
public class BidPreference {

    private QualificationTitle qualification;
    private int competency;
    private String subject;
    private BidInfo preferences;

    public BidPreference(QualificationTitle qualification, int competency, String subject, BidInfo preferences) {
        this.qualification = qualification;
        this.competency = competency;
        this.subject = subject;
        this.preferences = preferences;
    }
}
