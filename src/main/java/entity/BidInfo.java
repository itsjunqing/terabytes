package entity;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A BidInfo class representing the info (offer) of a Bid initiated by student / tutor.
 * Information could be a Bid information preferred by Tutor or offered from Tutor to Student.
 */
@Data @NoArgsConstructor
public class BidInfo {

    private String initiatorId;
    private String day;
    private String time;
    private int duration; // in hours
    private int rate; // rate per session
    private int numberOfSessions; // number of weeks
    private boolean freeLesson; // for tutor only

    /**
     * Constructor used for Student's preference
     */
    public BidInfo(String initiatorId, String day, String time, int duration, int rate, int numberOfSessions) {
        this.initiatorId = initiatorId;
        this.day = day;
        this.time = time;
        this.duration = duration;
        this.rate = rate;
        this.numberOfSessions = numberOfSessions;
    }

    /**
     * Constructor used for Tutor's offer
     */
    public BidInfo(String initiatorId, String day, String time, int duration, int rate, int numberOfSessions, boolean freeLesson) {
        this.initiatorId = initiatorId;
        this.day = day;
        this.time = time;
        this.duration = duration;
        this.rate = rate;
        this.numberOfSessions = numberOfSessions;
        this.freeLesson = freeLesson;
    }

}
