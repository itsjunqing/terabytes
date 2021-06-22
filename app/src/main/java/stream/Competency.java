package stream;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * A Competency data class.
 */
@Data @AllArgsConstructor
public class Competency {
    private String id;
    private User owner;
    private String ownerId;
    private Subject subject;
    private String subjectId;
    private Integer level;

    /**
     * Constructor for POST (Serialization)
     */
    public Competency(String ownerId, String subjectId, Integer level) {
        this.ownerId = ownerId;
        this.subjectId = subjectId;
        this.level = level;
    }
}
