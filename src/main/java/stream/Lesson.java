package stream;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * A Lesson data class, storing the information of a Lesson.
 */
@Data @AllArgsConstructor
public class Lesson {
    private String subject;
    private String day;
    private String time;
    private Integer duration; // hours
    private Integer numberOfSessions;
    private Boolean freeLesson;

}
