package test;


/**
 * A Bid data class, storing the information of a Bid such as preferences and offers.
 */

public class Bid {
    private String id;
    private String type;
    private String initiatorId;
    private String subjectId;

    /**
     * Constructor used to GET Bid (Deserialization)
     */
    public Bid(String id, String type) {
        this.id = id;
        this.type = type;
    }

    /**
     * Constructor used for creating and POST new Bid (Serialization)
     */
    public Bid(String type, String initiatorId, String subjectId) {
        this.type = type;
        this.initiatorId = initiatorId;
        this.subjectId = subjectId;
    }

    /**
     * Constructor for PATCH Bid (Serialization)
     */

    /**
     * Constructor used for mark Bid as closed down (Serialization)
     */


    public String toString(){
        return type + " bid for " + subjectId + " by " + initiatorId;
    }

}
