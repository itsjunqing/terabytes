package deserialization;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class User {
    private String id;
    private String givenName;
    private String familyName;
    private Boolean isStudent;
    private Boolean isTutor;
    private List<Competency> competencies;
    private List<Qualification> qualifications;
}
