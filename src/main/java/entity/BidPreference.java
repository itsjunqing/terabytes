package entity;

import lombok.Data;

/**
 * A BidPreference class representing the preference of a Bid.
 * Used by Student upon initiation of Bid.
 */
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
