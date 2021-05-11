package stream;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * A Subject data class.
 */
@Data @AllArgsConstructor
public class Subject {
    private String id;
    private String name;
    private String description;

    private List<Competency> competencies;
    private List<Bid> bids;

    public Subject(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
