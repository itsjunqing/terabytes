package stream;

import com.google.gson.annotations.SerializedName;
import entity.Preference;
import lombok.Data;

import java.util.Date;

/**
 * A Contract data class, storing the information of a Contract.
 */
@Data
public class Contract {
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
//    private EmptyClass preference;

    @SerializedName("additionalInfo")
    private Preference preference;

    /**
     * Constructor for GET Contract (Deserialization)
     */
    public Contract(String id, User firstParty, User secondParty, Subject subject, Date dateCreated,
                    Date dateSigned, Date expiryDate, Payment paymentInfo, Lesson lessonInfo,
                    Preference preference) {
        this.id = id;
        this.firstParty = firstParty;
        this.secondParty = secondParty;
        this.subject = subject;
        this.dateCreated = dateCreated;
        this.dateSigned = dateSigned;
        this.expiryDate = expiryDate;
        this.paymentInfo = paymentInfo;
        this.lessonInfo = lessonInfo;
        this.preference = preference;
    }

    /**
     * Constructor for POST new Contract (Serialization)
     */
    public Contract(String firstPartyId, String secondPartyId, String subjectId, Date dateCreated,
                    Date expiryDate, Payment paymentInfo, Lesson lessonInfo, Preference preference) {
        this.firstPartyId = firstPartyId;
        this.secondPartyId = secondPartyId;
        this.subjectId = subjectId;
        this.dateCreated = dateCreated;
        this.expiryDate = expiryDate;
        this.paymentInfo = paymentInfo;
        this.lessonInfo = lessonInfo;
        this.preference = preference;
    }

    /**
     * Constructor for signing Contract (Serialization)
     */
    public Contract(Date dateSigned) {
        this.dateSigned = dateSigned;
    }

    /**
     * Copy constructor that copies a Contract object for Renewing (POST) new contract
     */
    public Contract(Contract contract) {
        this.firstPartyId = contract.getFirstParty().getId();
        this.secondPartyId = contract.getSecondParty().getId();
        this.subjectId = contract.getSubject().getId();
        this.dateCreated = new Date();
        this.expiryDate = new Date();
        this.paymentInfo = new Payment(contract.getPaymentInfo());
        this.lessonInfo = new Lesson(contract.getLessonInfo());
        this.preference = new Preference(contract.getPreference());
    }
}
