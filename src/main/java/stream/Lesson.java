package stream;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class Lesson {
    // basically a replication of BidInfo, but without the message
    private String subject;
    private String day;
    private String time;
    private Integer duration; // in hours
    private Integer numberOfSessions;
    private Boolean freeLesson;

}
