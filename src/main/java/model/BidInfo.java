package model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
/**
 * Class representing the info (offer) of a Bid initiated by student / tutor
 */
public class BidInfo {

    private String initiatorId;
    private String time;
    private String day;
    private int duration; // in hours
    private int rate; // rate per hour
    private int numberOfSessions;
    private boolean freeLesson;
    private int contractDuration; // in weeks
    private boolean bidSelected; // not too sure if we should keep this
    private String parsedMessage;

    public BidInfo(String initiatorId, String time, String day, int duration, int rate, int numberOfSessions,
                   boolean freeLesson, int contractDuration) {
        this.initiatorId = initiatorId;
        this.time = time;
        this.day = day;
        this.duration = duration;
        this.rate = rate;
        this.numberOfSessions = numberOfSessions;
        this.freeLesson = freeLesson;
        this.contractDuration = contractDuration;
        this.bidSelected = false;
    }

    public BidInfo(String initiatorId, String time, String day, int duration, int rate, int numberOfSessions, int contractDuration) {
        this.initiatorId = initiatorId;
        this.time = time;
        this.day = day;
        this.duration = duration;
        this.rate = rate;
        this.numberOfSessions = numberOfSessions;
        this.contractDuration = contractDuration;
    }
}
