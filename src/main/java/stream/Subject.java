package stream;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class Subject {
    private String id;
    private String name;
    private String description;

    private List<Competency> competencies;
    private List<Bid> bids;
}
