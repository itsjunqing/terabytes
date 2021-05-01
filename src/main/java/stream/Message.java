package stream;

import lombok.Getter;

import java.util.Date;

@Getter
public class Message extends Stream {
    /*
    How this work:
    1) Student send Message to tutor, can only send a String message, so:
        - posterId will be student's ID
        - content will be student's message to Tutor
        - receiverId in additionalInfo will be Tutor's id

    2) Tutor send Message to student, can send a String mesage + information of an offer, so:
        - posterId will be tutor's ID
        - content will be tutor's message to Student
        - receiverId in additionalInfo will be Student's id
        - information of a bid offer is in additionalInfo

    Irregardless, the following will hold at all possible situations:
    - posterId is the sender's ID
    - receiverId in additionalInfo is receiver's ID
    - content to hold the message from sender to receiver
    - only bid offer information will hold WHEN posterId is a tutor.
     */
    private String id;
    private String bidId;

    private User poster;
    private String posterId; // poster = target

    private Date datePosted;
    private Date dateLastEdited;
    private String content; // may not use this
    private MessageAdditionalInfo additionalInfo;

    /**
     * Constructor for GET Message (Deserialization)
     */
    public Message(String id, String bidId, User poster, Date datePosted, Date dateLastEdited,
                   String content, MessageAdditionalInfo additionalInfo) {
        this.id = id;
        this.bidId = bidId;
        this.poster = poster;
        this.datePosted = datePosted;
        this.dateLastEdited = dateLastEdited;
        this.content = content;
        this.additionalInfo = additionalInfo;
    }

    /**
     * Constructor for POST new Message (Serialization)
     */
    public Message(String bidId, String posterId, Date datePosted, String content,
                   MessageAdditionalInfo additionalInfo) {
        this.bidId = bidId;
        this.posterId = posterId;
        this.datePosted = datePosted;
        this.content = content;
        this.additionalInfo = additionalInfo;
    }

    /**
     * Constructor for PATCH Message (Serialization)
     */
    public Message(String content, MessageAdditionalInfo additionalInfo) {
        this.content = content;
        this.additionalInfo = additionalInfo;
    }
}
