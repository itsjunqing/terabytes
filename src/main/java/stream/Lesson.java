package stream;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class Lesson {

    private String time;
    private String day;
    private int duration; // in hours
    private int numberOfSessions;
    private boolean freeLesson;

}
