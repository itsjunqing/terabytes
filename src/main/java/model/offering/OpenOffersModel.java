package model.offering;

import api.BidApi;
import entity.BidInfo;

import java.util.List;

public class OpenOffersModel {

    private String bidId;
    private BidApi bidApi;
    private BidInfo myOffer;
    private List<BidInfo> openOffers;

    public OpenOffersModel(String bidId) {
        this.bidId = bidId;
        this.bidApi = new BidApi();
    }




}
