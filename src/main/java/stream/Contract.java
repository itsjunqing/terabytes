package stream;

import lombok.Getter;

import java.util.Date;

@Getter
public class Contract extends Stream {
    private String id;

    private User firstParty;
    private String firstPartyId;

    private User secondParty;
    private String secondPartyId;

    private Subject subject;
    private String subjectId;

    private Date dateCreated;
    private Date dateSigned;
    private Date expiryDate;

    private Payment paymentInfo;
    private Lesson lessonInfo;
    private EmptyClass additionalInfo;

    /**
     * Constructor for GET Contract (Deserialization)
     */
    public Contract(String id, User firstParty, User secondParty, Subject subject, Date dateCreated,
                    Date dateSigned, Payment paymentInfo, Lesson lessonInfo, EmptyClass additionalInfo) {
        this.id = id;
        this.firstParty = firstParty;
        this.secondParty = secondParty;
        this.subject = subject;
        this.dateCreated = dateCreated;
        this.dateSigned = dateSigned;
        this.paymentInfo = paymentInfo;
        this.lessonInfo = lessonInfo;
        this.additionalInfo = additionalInfo;
    }

    /**
     * Constructor for POST new Contract (Serialization)
     */
    public Contract(String firstPartyId, String secondPartyId, String subjectId, Date dateCreated,
                    Date expiryDate, Payment paymentInfo, Lesson lessonInfo, EmptyClass additionalInfo) {
        this.firstPartyId = firstPartyId;
        this.secondPartyId = secondPartyId;
        this.subjectId = subjectId;
        this.dateCreated = dateCreated;
        this.expiryDate = expiryDate;
        this.paymentInfo = paymentInfo;
        this.lessonInfo = lessonInfo;
        this.additionalInfo = additionalInfo;
    }


    /**
     * Constructor for signing Contract (Serialization)
     */
    public Contract(Date dateSigned) {
        this.dateSigned = dateSigned;
    }

}
