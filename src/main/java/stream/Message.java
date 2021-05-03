package stream;

import lombok.Data;

import java.util.Date;

/**
 * A Message data class.
 * When Student sends a Message to Tutor, it sends only a String content.
 * When Tutor sends a Message to Student, it sends a String content + information of an offer.
 */
@Data
public class Message {

    private String id;
    private String bidId;

    private User poster;
    private String posterId;

    private Date datePosted;
    private Date dateLastEdited;
    private String content;
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
