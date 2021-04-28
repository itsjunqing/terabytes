package model;

import api.BidApi;
import api.MessageApi;
import lombok.Getter;
import lombok.Setter;
import observer.OSubject;
import stream.Bid;
import stream.BidAdditionalInfo;
import stream.User;

import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
public class OfferingModel extends OSubject {

    private User currentUser;
    private BidApi bidApi;
    private MessageApi messageApi;
    private List<Bid> bidsOnGoing;

    public OfferingModel(User currentUser) {
        this.currentUser = currentUser; // only Tutor can initialize this, so User is a tutor
        this.bidApi = new BidApi();
    }

    public void refresh() {

        bidsOnGoing.clear(); // for memory cleaning

        // Filter to ensure the tutor only gets initDisplay if the tutor has the qualification / competency
        // If yes, then tutor can proceed to offer / reply / buy out instantly
        bidsOnGoing = bidApi.getAllBids().stream().filter(b -> {
            BidPreference bidPreference = b.getAdditionalInfo().getBidPreference();

            // filter to check if the User has the qualification before initDisplay
            boolean hasQualification = currentUser.getQualifications().stream()
                    .anyMatch(q -> q.getTitle().equals(bidPreference.getQualification().toString()));

            // filter to check if the User has the competency of the subject before initDisplay
            boolean hasCompetency = currentUser.getCompetencies().stream()
                    .anyMatch(c -> c.getLevel() == bidPreference.getCompetency()
                            && c.getSubject().getName().equals(bidPreference.getSubject()));

            return hasQualification & hasCompetency;})
            .collect(Collectors.toList());

        notifyObservers();
    }

    public List<Bid> getOpenBids() {
        // to be used by view upon initDisplay
        return bidsOnGoing.stream()
                .filter(b -> b.getType().equals("Open"))
                .collect(Collectors.toList());
    }

    public List<Bid> getCloseBids() {
        // to be used by view upon initDisplay
        return bidsOnGoing.stream()
                .filter(b -> b.getType().equals("Close"))
                .collect(Collectors.toList());
    }

    public void sendOffer(int bidIndex, BidInfo bidInfo) {
        Bid bidChosen = getOpenBids().get(bidIndex);
        BidAdditionalInfo bidAdditionalInfo = bidChosen.getAdditionalInfo();
        bidAdditionalInfo.getBidOffers().add(bidInfo); // add offer

        // can only patch bid because api doesn't allow adding of information of the additionalInfo attribute
        bidApi.patchBid(bidChosen.getId(), new Bid(bidAdditionalInfo));
    }

    public void sendMessage(int bidIndex, BidInfo bidInfo) {
//        Bid bidChosen = getCloseBids().get(bidIndex);
//        String posterId = bidInfo.getInitiatorId();
//        Date datePosted = new Date();
//
//        // content will not be used, will use BidMessageInfo entirely
//        Message message = new Message(bidChosen.getId(), posterId, datePosted, "", bidInfo);
//        messageApi.addMessage(message);
    }




}
