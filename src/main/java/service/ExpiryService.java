package service;

import entity.BidInfo;
import entity.Constants;
import stream.Bid;
import stream.Contract;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * An ExpiryService class that performs a check on the Bid to see if it has expired or close down.
 */
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

    /**
     * Returns true if a bid is expired.
     * @param bid a bid Object
     * @return true if the bid is expired
     */
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
}
