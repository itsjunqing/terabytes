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
    private Boolean verified;
    private User owner;
    private String ownerId;

    /**
     * Constructor to POST Qualification
     */
    public Qualification(String title, String description, Boolean verified, String ownerId) {
        this.title = title;
        this.description = description;
        this.verified = verified;
        this.ownerId = ownerId;
    }
}
