package model;

import api.BidApi;
import api.ContractApi;
import entity.BidInfo;
import stream.*;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class CheckExpired {
    BidApi bidApi;
    ContractApi contractApi;
    public CheckExpired(){
        this.bidApi = new BidApi();
        this.contractApi = new ContractApi();
    }

    public boolean checkIsExpired(Bid bid){
        // if not closed
        if (bid.getDateClosedDown() == null) {
            System.out.println("Bid is not closed down");
            // if open
            if (bid.getType().equals("Open")) {
                System.out.println("Bid is Open bid");
                // if is expired
                if (isExpired(bid)) {
                    System.out.println("Bid expired");
                    // if has offer, get latest offer
                    if (hasOffer(bid)) {
                        System.out.println("Bid has offer");
                        // close the bid
                        bidApi.closeBid(bid.getId(), bid);
                        // get last bid
                        BidInfo bidInfo = bid.getAdditionalInfo().getBidOffers().get(bid.getAdditionalInfo().getBidOffers().size() - 1);
                        // create contract
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
                        contractApi.addContract(contract);
                        return true;
                    }
                    // if no offer, close
                    else {
                        System.out.println("Bid has no offer");
                        bidApi.closeBid(bid.getId(), bid);
                        return true;

                    }
                }// if not expired pass
                else{
                    return false;
                }
            }
            // if close
            else {
                System.out.println("Bid is a closed bid");
                // if is expired, close bid
                if (isExpired(bid)) {
                    System.out.println("Bid is expired");
                    bidApi.closeBid(bid.getId(), bid);
                    return true;
                    // close bid
                } else {
                    return false;
                }
                // if not expired pass
            }
        }else {
            return false;
        }
    }

    public boolean isExpired(Bid bid){
        Date then = bid.getDateCreated();
        Date now = new Date();
        long difference = now.getTime() - then.getTime();
        long minuteDifference = TimeUnit.MILLISECONDS.toMinutes(difference);
        long dayDifference = TimeUnit.MILLISECONDS.toDays(difference);
        if (bid.getType().equals("Open") ) {
            return minuteDifference > 30;
        } else {
            return dayDifference > 7;
        }
    }
    // true if has offer, false otherwise
    public boolean hasOffer(Bid bid) {
        return bid.getAdditionalInfo().getBidOffers().size() != 0;
    }


}
