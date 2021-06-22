package entity;

import lombok.Data;

/**
 * A MessagePair data class representing the pair of Message sent from one Student to one Tutor.
 * Each Student can have one pair with every Tutor.
 * Every Tutor can have one pair with every Student.
 */
@Data
public class MessagePair {

    private String tutorMsgId;
    private MessageBidInfo tutorMsg;
    private String studentMsgId;
    private MessageBidInfo studentMsg;

    public MessagePair(String tutorMsgId, MessageBidInfo tutorMsg, String studentMsgId, MessageBidInfo studentMsg) {
        this.tutorMsgId = tutorMsgId;
        this.tutorMsg = tutorMsg;
        this.studentMsgId = studentMsgId;
        this.studentMsg = studentMsg;
    }
}
