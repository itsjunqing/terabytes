package model;

import lombok.Data;

@Data
public class MessagePair {
    private MessageBidInfo tutorMsg;
    private MessageBidInfo studentMsg;

    public MessagePair(MessageBidInfo tutorMsg, MessageBidInfo studentMsg) {
        this.tutorMsg = tutorMsg;
        this.studentMsg = studentMsg;
    }
}
