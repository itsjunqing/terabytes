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
    private Subject subject;
    private Integer level;
}
