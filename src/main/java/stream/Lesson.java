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

    /**
     * Copy constructor that copies a Lesson object.
     * @param lesson a Lesson object
     */
    public Lesson(Lesson lesson) {
        this.subject = lesson.subject;
        this.day = lesson.day;
        this.time = lesson.time;
        this.duration = lesson.duration;
        this.numberOfSessions = lesson.numberOfSessions;
        this.freeLesson = lesson.freeLesson;
    }

}
