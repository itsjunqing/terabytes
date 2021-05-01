package api;

import stream.Bid;

import java.util.List;

public class BidApi extends BasicApi<Bid> {

    private final String BID_ENDPOINT = "/bid";
    private final String BID_PARAMETERS = "?fields=messages";

    @Override
    public List<Bid> getAll() {
        return getAllObjects(BID_ENDPOINT + BID_PARAMETERS, Bid[].class);
    }

    @Override
    public Bid get(String id) {
        String endpoint = BID_ENDPOINT + "/" + id + BID_PARAMETERS;
        return getObject(endpoint, Bid.class);
    }

    @Override
    public Bid add(Bid object) {
        return postObject(BID_ENDPOINT, object, Bid.class);
    }

    @Override
    public boolean patch(String id, Bid object) {
        String endpoint = BID_ENDPOINT + "/" + id;
        return patchObject(endpoint, object);
    }

    @Override
    public boolean remove(String id) {
        return deleteObject(BID_ENDPOINT + "/" + id);
    }

    public boolean close(String id, Bid object) {
        String endpoint = BID_ENDPOINT + "/" + id + "/close-down";
        return postObject(endpoint, object);
    }

}
