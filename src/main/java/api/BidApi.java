package api;

import stream.Bid;

import java.util.List;

public class BidApi extends BasicApi<Bid> {

    private final String BID_ENDPOINT = "/bid";

    public List<Bid> getAllBids() {
        return getAllObjects(BID_ENDPOINT + "?fields=messages", Bid[].class);
    }

    public Bid getBid(String id) {
        String endpoint = BID_ENDPOINT + "/" + id + "?fields=messages";
        return getObject(endpoint, Bid.class);
    }

    public boolean addBid(Bid bid) {
        return postObject(BID_ENDPOINT, bid);
    }

    public boolean patchBid(Bid bid) {
        return patchObject(BID_ENDPOINT, bid);
    }

    public boolean removeBid(String id) {
        return deleteObject(BID_ENDPOINT + "/" + id);
    }

    public boolean closeBid(String id, Bid bid) {
        String endpoint = BID_ENDPOINT + "/" + id + "/close-down";
        return postObject(endpoint, bid);
    }
}
