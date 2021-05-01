package stream;

import lombok.Getter;

import java.util.List;

@Getter
public class User extends Stream {
    private String id;
    private String givenName;
    private String familyName;

    private String userName;
    private String password;

    private Boolean isStudent;
    private Boolean isTutor;

    private List<Competency> competencies;
    private List<Qualification> qualifications;
    private List<Bid> initiatedBids;

    /**
     * Constructor for GET User (Deserialization)
     */
    public User(String id, String givenName, String familyName, String userName,
                Boolean isStudent, Boolean isTutor, List<Competency> competencies,
                List<Qualification> qualifications, List<Bid> initiatedBids) {
        this.id = id;
        this.givenName = givenName;
        this.familyName = familyName;
        this.userName = userName;
        this.isStudent = isStudent;
        this.isTutor = isTutor;
        this.competencies = competencies;
        this.qualifications = qualifications;
        this.initiatedBids = initiatedBids;
    }

    /**
     * Constructor for GET User (Deserialization)
     */
    public User(String id, String givenName, String familyName, String userName,
                Boolean isStudent, Boolean isTutor) {
        this.id = id;
        this.givenName = givenName;
        this.familyName = familyName;
        this.userName = userName;
        this.isStudent = isStudent;
        this.isTutor = isTutor;
    }

    /**
     * Constructor for verifying user credentials (Serialization)
     */
    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}
