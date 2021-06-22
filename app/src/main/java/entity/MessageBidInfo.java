package entity;

import lombok.Data;

/**
 * A MessageBidInfo class representing the Message's version of BidInfo.
 * Used in Close Bid where Tutor sends a Bid information to a Student.
 */
@Data
public class MessageBidInfo extends BidInfo {

    private String content;

    /**
     * Constructor used for Student's message
     */
    public MessageBidInfo(String initiatorId, String day, String time, int duration,
                          int rate, int numberOfSessions, String content) {
        super(initiatorId, day, time, duration, rate, numberOfSessions);
        this.content = content;
    }

    /**
     * Constructor used for Tutor's message
     */
    public MessageBidInfo(String initiatorId, String day, String time, int duration, int rate,
                          int numberOfSessions, boolean freeLesson, String content) {
        super(initiatorId, day, time, duration, rate, numberOfSessions, freeLesson);
        this.content = content;
    }

    /**
     * Returns the string of MessageBidInfo
     * @return a String
     */
    @Override
    public String toString() {
        return "MessageBidInfo(" +
                "initiatorId='" + getInitiatorId() + '\'' +
                ", day='" + getDay() + '\'' +
                ", time='" + getTime() + '\'' +
                ", duration=" + getDuration() +
                ", rate=" + getRate() +
                ", numberOfSessions=" + getNumberOfSessions() +
                ", freeLesson=" + isFreeLesson() +
                ", content='" + content + '\'' +
                ')';
    }
}
