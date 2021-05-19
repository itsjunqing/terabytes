package model.offering;

import lombok.Getter;
import model.BasicModel;
import observer.Observer;
import service.ApiService;
import stream.Bid;

import java.util.ArrayList;
import java.util.List;


/**
 * A Class of MonitoringModel that stores the information that monitors on-going Bids.
 *
 * MonitoringModel is both a Subject and an Observer.
 * Subject: To be observed by the View to update the UI
 * Observer: To observe the scheduler to be notified and updated whenever the Scheduler calls
 */
@Getter
public class MonitoringModel extends BasicModel implements Observer {

    private List<Bid> monitoringBids;

    public MonitoringModel(String userId, List<Bid> monitoringBids) {
        this.userId = userId;
        this.monitoringBids = new ArrayList<>(monitoringBids);
    }

    @Override
    public void refresh() {
        System.out.println("From MonitoringModel: Refreshing content...");
        List<Bid> updateBids = new ArrayList<>();
        for (Bid bid: monitoringBids) {
            Bid latestBid = ApiService.bidApi().get(bid.getId());
            // If the bid is not closed down yet, display it
            if (latestBid.getDateClosedDown() == null) {
                updateBids.add(latestBid);
            }
        }
        monitoringBids.clear(); // Remove old content
        monitoringBids.addAll(updateBids); // Add the updated Bid
        oSubject.notifyObservers();
    }

    /**
     * Function called by scheduler to update the model
     */
    @Override
    public void update() {
        refresh();
    }

    public Bid viewOffers(int selection){
        Bid bid = monitoringBids.get(selection-1);
        if (!expiryService.checkIsExpired(bid)){
            return bid;
        }
        return null;
    }


}


//    public void setSelectedBids(List<Bid> selectedBids){
//        this.selectedBids = selectedBids;
//        selectedBidIds.clear();
//        selectedBids.stream().forEach(b -> selectedBidIds.add(b.getId()));
//    }

//    /**
//     * Get list of bids that are ongoing and save it to class variable
//     * get bidsOngoing
//     */
//    private List<Bid> processBids(List<Bid> bids) {
//        List<Bid> outputBidList = new ArrayList<Bid>();
//        outputBidList.clear(); // for memory cleaning
//        User currentUser = ApiService.userApi().get(userId);
//        ExpiryService expiryService = new ExpiryService();
//        for (Bid b: bids) {
//            if (!expiryService.checkIsExpired(b)) {
//                Preference bp = b.getAdditionalInfo().getPreference();
//                boolean hasQualification = currentUser.getQualifications().stream()
//                        .anyMatch(q -> q.getTitle().equals(bp.getQualification().toString()));
//
//                // Bonus mark on checking 2 levels higher for competency requirement
//                boolean hasCompetency = currentUser.getCompetencies().stream()
//                        .anyMatch(c -> c.getLevel() - 2 >= bp.getCompetency()
//                                && c.getSubject().getName().equals(bp.getSubject()));
//                if (hasQualification && hasCompetency) {
////                    Bid filteredBid = filterBidOffers(b);
//                    outputBidList.add(b);
//                }
//            }
//        }
//        return outputBidList;
//    }
//
//
//    // TODO DELETE FUNCTION
//    /**
//     * Function to check if bid is expired and to only
//     * @param bid
//     * @return a bid without offers given by this tutor
//     */
//    private Bid filterBidOffers(Bid bid) {
//        List<BidInfo> allOffers = new ArrayList<BidInfo>();
//        Bid returnBid = bid;
//        List<BidInfo> offers = new ArrayList<>(bid.getAdditionalInfo().getBidOffers());
//        System.out.println("From OpenOfferModel Refreshing..");
//        ExpiryService expiryService = new ExpiryService();
//        // if bid has expired, close down the bid
//        if (!expiryService.checkIsExpired(bid)){
//            for (BidInfo bidInfo: offers) {
//                // myOffer is BidInfo offered by itself
//                if (!bidInfo.getInitiatorId().equals(userId)) {
//                    // openOffers includes all the BidInfo offers (by all tutors) except the current tutor
//                    allOffers.add(bidInfo);
//                }
//            }
//            returnBid.getAdditionalInfo().setBidOffers(allOffers);
//        } else{
//            returnBid = null;
//        }
//        return returnBid;
//    }
//
//
//
//    /**
//     * Gets the Bid object of the corresponding offer
//     * @return a Bid object
//     */
//    public Bid getBid(String bidId) {
//        return ApiService.bidApi().get(bidId);
//    }
//
//    /**
//     * Buy out a Bid
//     */
//    public void buyOut(String bidId){
//        Bid bid = getBid(bidId);
//        if (!expiryService.checkIsExpired(bid)){
//            Preference bp = bid.getAdditionalInfo().getPreference();
//            BidInfo bidInfo = bp.getPreferences();
//            bidInfo.setInitiatorId(getUserId());
//            sendOffer(bidInfo, bidId);
//            Contract contract = BuilderService.buildContract(bid, bidInfo);
//            // logic to post contract
//            Contract contractCreated = ApiService.contractApi().add(contract);
//
//            // add 10 seconds to contract signing as signDate > creationDate
//            Calendar c = Calendar.getInstance();
//            c.setTime(new Date());
//            c.add(Calendar.SECOND, 10);
//            ApiService.contractApi().sign(contractCreated.getId(), new Contract(c.getTime()));
//
//            // mark bid as closed
//            ApiService.bidApi().close(bidId, new Bid(new Date()));
//        } else {
//            errorText = "Bid Has Expired";
//            oSubject.notifyObservers();
//        }
//    }
//
//    /**
//     * Respond to the Bid by providing an offer contained within a BidInfo
//     * @param bidInfo a BidInfo object
//     */
//    public void respond(BidInfo bidInfo, String selectedBidId) {
//        if (!expiryService.checkIsExpired(getBid(selectedBidId))) {
//            sendOffer(bidInfo, selectedBidId);
//            System.out.println("providing offer");
//        } else {
//            errorText = "Bid Has Expired";
//        }
//        refresh();
//    }
//
//    /**
//     * Sends an offer to the student by patching to the API.
//     * @param bidInfo a BidInfo object
//     */
//    private void sendOffer(BidInfo bidInfo, String selectedBidId) {
//        // Update offer
//        BidAdditionalInfo info = ApiService.bidApi().get(selectedBidId).getAdditionalInfo();
//        BidInfo currentBidInfo = info.getBidOffers().stream()
//                .filter(i -> i.getInitiatorId().equals(userId))
//                .findFirst()
//                .orElse(null);
//        // if the tutor has provided an offer before, remove the offer
//        if (currentBidInfo != null) {
//            info.getBidOffers().remove(currentBidInfo);
//        }
//        info.getBidOffers().add(bidInfo);
//        ApiService.bidApi().patch(selectedBidId, new Bid(info));
//    }



