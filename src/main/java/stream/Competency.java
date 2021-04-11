package stream;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class Competency {
    private String id;
    private User owner;
    private Subject subject;
    private int level;
}
