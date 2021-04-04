package deserialization;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data @AllArgsConstructor
public class Bid {
    private String id;
    private String type;
    private User initiator;
    private Date dateCreated;
    private Date dateClosedDown;
    private Subject subject;
    private Extra additionalInfo;

    // TODO: Inner class below to be modified
    @Data
    private class Extra {
        private String info;
    }
}
