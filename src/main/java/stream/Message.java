package stream;

import lombok.Data;
import model.BidInfo;

import java.util.Date;

@Data
public class Message {
    private String id;
    private String bidId;

    private User poster;
    private String posterId;

    private Date datePosted;
    private Date dateLastEdited;
    private String content; // may not use this
    private BidInfo additionalInfo;

    /**
     * Constructor for GET Message (Deserialization)
     */
    public Message(String id, String bidId, User poster, Date datePosted, Date dateLastEdited,
                   String content, BidInfo additionalInfo) {
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
                   BidInfo additionalInfo) {
        this.bidId = bidId;
        this.posterId = posterId;
        this.datePosted = datePosted;
        this.content = content;
        this.additionalInfo = additionalInfo;
    }

    // TODO: Inner class below to be modified
    @Data
    private class Extra {
        private String info;
    }
}
