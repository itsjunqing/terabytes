package deserialization;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class Qualification {
    private String id;
    private String title;
    private String description;
    private Boolean verified;
    private User owner;
}
