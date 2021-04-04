package deserialization;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data @AllArgsConstructor
public class Message {
    private String id;
    private String bidId;
    private User poster;
    private Date datePosted;
    private Date dateLastEdited;
    private String content;
    private String additionalInfo;
}
