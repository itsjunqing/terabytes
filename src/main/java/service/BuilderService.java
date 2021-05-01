package service;

import entity.BidInfo;
import entity.BidPreference;
import stream.*;

import java.util.Calendar;
import java.util.Date;

/**
 * A builder class that builds objects to be re-used
 */
public class BuilderService {

    public static Bid buildBid(String userId, BidPreference bp, String type) {
        BidAdditionalInfo bidAdditionalInfo = new BidAdditionalInfo(bp);
        Date dateCreated = new Date();
        String subjectId = Service.subjectApi.getAll().stream()
                .filter(s -> s.getName().equals(bp.getSubject()))
                .findFirst()
                .orElse(null) // null guarantee to not occur as view selected is from a list of available subjects
                .getId();
        return new Bid(type, userId, dateCreated, subjectId, bidAdditionalInfo);
    }

    public static Contract buildContract(Bid bid, BidInfo offer) {
        String studentId = bid.getInitiator().getId();
        String tutorId = offer.getInitiatorId();
        String subjectId = bid.getSubject().getId();
        Date dateCreated = new Date();

        // take currentDate + number of sessions (weeks) to get expiry date
        Calendar c = Calendar.getInstance();
        c.setTime(dateCreated);
        c.add(Calendar.WEEK_OF_YEAR, offer.getNumberOfSessions());
        Date expiryDate = c.getTime();

        // calculate payment = rate per session * number of sessions
        Payment payment = new Payment(offer.getRate() * offer.getNumberOfSessions());
        Lesson lesson = new Lesson(bid.getSubject().getName(), offer.getDay(), offer.getTime(),
                offer.getDuration(), offer.getNumberOfSessions(), offer.isFreeLesson());

        return new Contract(studentId, tutorId, subjectId, dateCreated, expiryDate, payment, lesson, new EmptyClass());
    }


}
