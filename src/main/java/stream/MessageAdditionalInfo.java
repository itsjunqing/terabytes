package stream;

import lombok.Data;

@Data
public class MessageAdditionalInfo {

    private Boolean toStudent;
    private String receiverId;
    private String day;
    private String time;
    private Integer duration; // in hours
    private Integer rate; // rate per hour
    private Integer numberOfSessions;
    private Boolean freeLesson;

    /**
     * Constructor that sends a Message from Tutor to Student
     */
    public MessageAdditionalInfo(String receiverId, String day, String time, Integer duration, Integer rate,
                                 Integer numberOfSessions, Boolean freeLesson) {
        this.toStudent = true;
        this.receiverId = receiverId;
        this.day = day;
        this.time = time;
        this.duration = duration;
        this.rate = rate;
        this.numberOfSessions = numberOfSessions;
        this.freeLesson = freeLesson;
    }

    /**
     * Constructor that sends a Message from Student to Tutor
     */
    public MessageAdditionalInfo(String receiverId) {
        this.toStudent = false;
        this.receiverId = receiverId;
    }
}
