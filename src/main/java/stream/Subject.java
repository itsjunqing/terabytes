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
}
