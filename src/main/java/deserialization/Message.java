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
    private Extra additionalInfo;

    // TODO: Inner class below to be modified
    @Data
    private class Extra {
        private String info;
    }
}
