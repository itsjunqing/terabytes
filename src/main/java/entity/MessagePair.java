package entity;

import lombok.Data;

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
