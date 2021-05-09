package entity;

import lombok.Data;

/**
 * A Preference class representing the preference of a Bid.
 * Used by Student upon initiation of Bid.
 */
@Data
public class Preference {

    private QualificationTitle qualification;
    private int competency;
    private String subject;
    private BidInfo preferences;

    /**
     * Constructor for Preference
     * @param qualification the title of the Qualification
     * @param competency competency level
     * @param subject a string subject
     * @param preferences a
     */
    public Preference(QualificationTitle qualification, int competency, String subject, BidInfo preferences) {
        this.qualification = qualification;
        this.competency = competency;
        this.subject = subject;
        this.preferences = preferences;
    }

    /**
     * Copy constructor that copies a Preference object.
     * @param preference a Preference object
     */
    public Preference(Preference preference) {
        this.qualification = preference.qualification;
        this.competency = preference.competency;
        this.subject = preference.subject;
        this.preferences = preference.preferences; // object assignment, not reconstruction
    }
}
