package stream;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * A Qualification data class.
 */
@Data @AllArgsConstructor
public class Qualification {
    private String id;
    private String title;
    private String description;
    private boolean verified;
    private User owner;
}
