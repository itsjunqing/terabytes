package controller.bidding;

import entity.BidInfo;
import stream.*;

import java.util.Calendar;
import java.util.Date;

/**
 * Remaining parts:
 * - construction of ContractController, View, Model
 */
public abstract class BiddingController {

    /**
     * Creates Contract using the on-going Bid and a BidInfo
     * @param bid
     * @param bidInfo
     */
    protected void createContract(Bid bid, BidInfo bidInfo) {
        String studentId = bid.getInitiator().getId();
        String tutorId = bidInfo.getInitiatorId();
        String subjectId = bid.getSubject().getId();
        Date dateCreated = new Date();

        // take currentDate + number of sessions (weeks) to get expiry date
        Calendar c = Calendar.getInstance();
        c.setTime(dateCreated);
        c.add(Calendar.WEEK_OF_YEAR, bidInfo.getNumberOfSessions());
        Date expiryDate = c.getTime();

        // calculate payment = rate per session * number of sessions
        Payment payment = new Payment(bidInfo.getRate() * bidInfo.getNumberOfSessions());
        Lesson lesson = new Lesson(bid.getSubject().getName(), bidInfo.getDay(), bidInfo.getTime(),
                bidInfo.getDuration(), bidInfo.getNumberOfSessions(), bidInfo.isFreeLesson());
        Contract contract = new Contract(studentId, tutorId, subjectId, dateCreated,
                expiryDate, payment, lesson, new EmptyClass());
        handleContract(contract);
    }

    private void handleContract(Contract contract) {
        // TODO: handle Contract pushing + Contract MVC construction
    }

    public abstract void listenViewActions();

}
