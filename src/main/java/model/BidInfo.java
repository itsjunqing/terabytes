package model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
/**
 * Class representing the info (offer) of a Bid initiated by student / tutor
 */
public class BidInfo {

    // Attributes common to both student (bid) and tutor (offer)
    private String initiatorId;
    private String time;
    private String day;
    private int duration; // in hours
    private int rate; // rate per hour
    private int numberOfSessions;
    private int contractDuration; // in weeks
    private String parsedMessage; // only used for close bid

    // Attributes unique to tutor only
    private boolean freeLesson;
    private boolean bidSelected = false; // not too sure if we should keep this

    public BidInfo(String initiatorId, String time, String day, int duration, int rate,
                   int numberOfSessions, int contractDuration, String parsedMessage) {
        this.initiatorId = initiatorId;
        this.time = time;
        this.day = day;
        this.duration = duration;
        this.rate = rate;
        this.numberOfSessions = numberOfSessions;
        this.contractDuration = contractDuration;
        this.parsedMessage = parsedMessage;
    }
}
