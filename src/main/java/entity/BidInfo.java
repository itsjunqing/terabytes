package entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
/**
 * Class representing the info (offer) of a Bid initiated by student / tutor
 */
public class BidInfo {
    /*
    BidInfo contains the information of a Bid either:
    - preferred by Student
    - offered by Tutor to Student

    Note: no messages are included here.
     */
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
