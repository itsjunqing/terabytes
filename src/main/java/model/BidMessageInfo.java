package model;

import lombok.Data;

@Data
/**
 * A form of BidInfo but for messagings
 */
public class BidMessageInfo extends BidInfo {

    private String parsedMessage;

    public BidMessageInfo(int initiatorId, String time, String day, int duration, int rate, int numberOfSessions,
                          boolean freeLesson, int contractDuration, String parsedMessage) {
        super(initiatorId, time, day, duration, rate, numberOfSessions, freeLesson, contractDuration);
        this.parsedMessage = parsedMessage;
    }
}
