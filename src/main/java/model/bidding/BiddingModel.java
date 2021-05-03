package model.bidding;

import lombok.Getter;
import model.BasicModel;
import service.ApiService;
import stream.Bid;

import java.util.Date;

/**
 * A class of BiddingModel that stores the data of a Bidding.
 */
@Getter
public abstract class BiddingModel extends BasicModel {

    protected String bidId;

    /**
     * Extracts the current ongoing bid of a user (Student)
     * @param userId a String user id
     * @param type the type of the Bid (Open or Close)
     * @return a Bid object
     */
    protected Bid extractBid(String userId, String type) {
        return ApiService.bidApi().getAll().stream()
                .filter(b -> b.getDateClosedDown() == null)
                .filter(b -> b.getType().equalsIgnoreCase(type))
                .filter(b -> b.getInitiator().getId().equals(userId))
                .findFirst()
                .orElse(null); // can never exist
    }

    /**
     * Mark a Bid as close by posting to API.
     */
    public void markBidClose() {
        Bid bidDateClosed = new Bid(new Date());
        ApiService.bidApi().close(bidId, bidDateClosed);
    }

    /**
     * Returns the Bid object of current bid id
     * @return a Bid object
     */
    public Bid getBid() {
        return ApiService.bidApi().get(bidId);
    }

    /**
     * Returns the Date which the Bid was created
     * @return a Date
     */
    public Date getBidDate(){
        return ApiService.bidApi().get(bidId).getDateCreated();
    }


    /**
     * Contructs and returns a Contract object based on a BidInfo provided.
     * @param bidInfo a BidInfo object
     * @return a Contract object
     */


    /**
     * An abstract method that returns a BidInfo based on a selection.
     * @param selection an integer selection
     * @return a BidInfo object
     */
//    public abstract BidInfo selectOffer(int selection);
}
