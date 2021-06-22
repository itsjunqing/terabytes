package builder;

import entity.BidInfo;
import entity.Constants;
import entity.Utility;
import stream.Bid;
import stream.Contract;
import stream.Lesson;
import stream.Payment;

import java.util.Calendar;
import java.util.Date;

/**
 * Class that builds a Contract
 */
public class ContractBuilder {

    /**
     * Builds a Contract object with a default expiry (will require caller to set manually if expiry were to change)
     * @param bid a Bid object
     * @param offer an offer from tutor (when offering) or student (when buyout)
     * @return a Contract object
     */
    public Contract buildContract(Bid bid, BidInfo offer) {
        String studentId = bid.getInitiator().getId();
        String tutorId = offer.getInitiatorId();
        String subjectId = bid.getSubject().getId();
        Date dateCreated = new Date();

        // calculate expiry date based on date creation
        Calendar c = Calendar.getInstance();
        c.setTime(dateCreated);
        c.add(Calendar.MONTH, Constants.DEFAULT_CONTRACT_DURATION);
        Date expiryDate = c.getTime();

        // calculate payment = rate per session * number of sessions
        Payment payment = new Payment(offer.getRate() * offer.getNumberOfSessions());
        Lesson lesson = new Lesson(bid.getSubject().getName(), offer.getDay(), offer.getTime(),
                offer.getDuration(), offer.getNumberOfSessions(), offer.isFreeLesson());

        return new Contract(studentId, tutorId, subjectId, dateCreated, expiryDate, payment,
                lesson, bid.getAdditionalInfo().getPreference());
    }

    /**
     * Builds a Contract from an old Contract with new terms and new tutor
     * @param contract old contract
     * @param newTerms new terms to be established
     * @param tutorId tutor of the new contract
     * @return a Contract object
     */
    public Contract buildContract(Contract contract, BidInfo newTerms, String tutorId) {
        Contract newContract = new Contract(contract);

        // Calculate expiry date based on date creation
        Calendar c = Calendar.getInstance();
        c.setTime(newContract.getDateCreated());
        c.add(Calendar.MONTH, Constants.DEFAULT_CONTRACT_DURATION);
        Date expiryDate = c.getTime();

        // Calculate payment = rate * number of sessions
        Payment payment = new Payment(newTerms.getRate() * newTerms.getNumberOfSessions());

        // Get updated Lesson
        Lesson lesson = new Lesson(Utility.getSubjectName(newContract.getSubjectId()), newTerms.getDay(),
                newTerms.getTime(), newTerms.getDuration(), newTerms.getNumberOfSessions(), newTerms.isFreeLesson());

        // Update tutor + expiry date + payment + lesson
        newContract.setSecondPartyId(tutorId);
        newContract.setExpiryDate(expiryDate);
        newContract.setPaymentInfo(payment);
        newContract.setLessonInfo(lesson);

        return newContract;
    }

    /**
     * Builds a Contract from an old Contract with (old terms) but new tutor
     * @param contract old contract
     * @param tutorId tutor of the new contract
     * @return a Contract object
     */
    public Contract buildContract(Contract contract, String tutorId) {
        Contract newContract = new Contract(contract);

        // Calculate expiry date based on date creation
        Calendar c = Calendar.getInstance();
        c.setTime(newContract.getDateCreated());
        c.add(Calendar.MONTH, Constants.DEFAULT_CONTRACT_DURATION);
        Date expiryDate = c.getTime();

        // Update tutor + expiry date
        newContract.setSecondPartyId(tutorId);
        newContract.setExpiryDate(expiryDate);

        return newContract;
    }
}
