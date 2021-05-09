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
     * Constructor to create a Lesson (used for Contract renewal) in new terms establishment
     * @param subject
     * @param day
     * @param time
     * @param duration
     * @param numberOfSessions
     */
    public Lesson(String subject, String day, String time, Integer duration, Integer numberOfSessions) {
        this.subject = subject;
        this.day = day;
        this.time = time;
        this.duration = duration;
        this.numberOfSessions = numberOfSessions;
    }

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
