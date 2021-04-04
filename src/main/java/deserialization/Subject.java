package deserialization;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class Subject {
    private String id;
    private String name;
    private String description;
}
