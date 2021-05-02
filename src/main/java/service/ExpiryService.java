package service;

import entity.BidInfo;
import entity.Constants;
import stream.Bid;
import stream.Contract;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ExpiryService {

    public boolean checkIsExpired(Bid bid){
        if (bid.getDateClosedDown() != null) {
            System.out.println("From ExpiryService: Bid is Closed Down");
            return true;
        }
        if (!bidIsExpired(bid)) {
            System.out.println("From ExpiryService: Bid is Not Expired, to be displayed on View..");
            return false;
        }
        if (bid.getType().equals("Open")) {
            System.out.println("From ExpiryService: Bid is a Open Bid + Expired + Closing it..");
            List<BidInfo> offers = bid.getAdditionalInfo().getBidOffers();

            if (offers.size() > 0) {
                System.out.println("From ExpiryService: Bid is a Has Offer + creating Contract..");
                BidInfo lastBidInfo = offers.get(offers.size()-1);
                Contract contract = BuilderService.buildContract(bid, lastBidInfo);
                ApiService.contractApi().add(contract);
            } else {
                System.out.println("From ExpiryService: Bid has No Offer + doing nothing..");
            }
        } else {
            System.out.println("From ExpiryService: Bid is a Close Bid + Expired + Closing it..");
        }
        ApiService.bidApi().close(bid.getId(), new Bid(new Date()));
        return true;
    }

    private boolean bidIsExpired(Bid bid) {
        Date then = bid.getDateCreated();
        Date now = new Date();
        long difference = now.getTime() - then.getTime();
        long minuteDifference = TimeUnit.MILLISECONDS.toMinutes(difference);
        long dayDifference = TimeUnit.MILLISECONDS.toDays(difference);
        if (bid.getType().equals("Open") ) {
            return minuteDifference >= Constants.OPEN_BID_MINS;
        } else {
            return dayDifference >= Constants.CLOSE_BID_DAYS;
        }
    }





    /*


    OLD CODE


        // if not closed
        if (bid.getDateClosedDown() == null) {
            System.out.println("Bid is not closed down");
            // if open
            if (bid.getType().equals("Open")) {
                System.out.println("Bid is Open bid");
                // if is expired
                if (bidIsExpired(bid)) {
                    System.out.println("Bid expired");
                    // if has offer, get latest offer
                    if (hasOffer(bid)) {
                        System.out.println("Bid has offer");
                        // close the bid
                        bidApi.close(bid.getId(), bid);
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
                        contractApi.add(contract);
                        return true;
                    }
                    // if no offer, close
                    else {
                        System.out.println("Bid has no offer");
                        bidApi.close(bid.getId(), bid);
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
                if (bidIsExpired(bid)) {
                    System.out.println("Bid is expired");
                    bidApi.close(bid.getId(), bid);
                    return true;
                    // close bid
                } else {
                    return false;
                }
                // if not expired pass
            }
        }
        return false;

     */
}
