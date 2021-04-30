package controller.offering;

import entity.BidInfo;
import entity.MessageBidInfo;
import model.offering.OfferingModel;
import stream.Bid;
import view.form.OfferBid;
import view.form.ReplyBid;
import view.offering.CloseOfferView;
import view.offering.OfferingView;
import view.offering.OpenOffersView;

import java.awt.event.ActionEvent;
import java.util.List;
//import view.offering.OfferingView;

public class OfferingController {

    private OfferingModel offeringModel;
    private OfferingView offeringView;
    private OpenOffersView openOffersView;
    private CloseOfferView closeOfferView;
    private String userId;

    public OfferingController(String userId) {
        this.offeringModel = new OfferingModel(userId);
        this.offeringView = new OfferingView(offeringModel);
        listenViewActions();
    }

    private void listenViewActions() {
        offeringView.getRefreshButton().addActionListener(this::handleRefresh);
        offeringView.getViewOffersButton().addActionListener(this::handleViewOffers);
    }

    private void handleRefresh(ActionEvent e) {
        System.out.println("From OfferingController: Refresh Button is pressed");
        offeringModel.refresh();
    }

    private void handleViewOffers(ActionEvent e) {
        System.out.println("From OfferingController: ViewOffers Button is pressed");
        int selection = offeringView.getBidNumber();
        Bid bid = offeringModel.getBidsOnGoing().get(selection-1);
        if (bid.getType().equals("Open")) {
            OpenOffersController openOffersController = new OpenOffersController(bid.getId());
        } else {

        }

    }


    public void listenRefresh() {
        offeringModel.refresh();
    }

    private void listenOfferingView(){
        this.offeringView.getViewOffersButton().addActionListener(e -> {
            int selectedBid = this.offeringView.getBidNumber();
            List<Bid> ongoing = offeringModel.getBidsOnGoing();
            Bid selected = ongoing.get(selectedBid-1);
        if (selected.getType().equals("Open")) {
            openOffersView= new OpenOffersView(offeringModel, selectedBid);
            listenOpenOffers(selectedBid);
        }else {
            closeOfferView = new CloseOfferView(offeringModel, selectedBid);
            listenCloseOffers(selectedBid);
        }
        });

    }
    private void listenOpenOffers(int selectedBid){

        openOffersView.getRespondButton().addActionListener(e -> {
            OfferBid offerBidForm = new OfferBid();
            offerBidForm.getOfferBidButton().addActionListener(ef -> {
                try {
                    BidInfo bo = extractBidOfferInfo(offerBidForm);
                    System.out.println("Extracted: " + bo);
                    initiateOpenOffer(selectedBid, bo);
                    offerBidForm.dispose();
                } catch (NullPointerException exception) {
                    // TODO: Add error message in UI on incomplete forms, similar to login
                }
            });});


    };

    private void listenCloseOffers(int selectedBid) {
        closeOfferView.getRespondMessageButton().addActionListener(e -> {
            ReplyBid replyBidForm = new ReplyBid();
            replyBidForm.getSendReplyButton().addActionListener(ef -> {
                try {
                    MessageBidInfo bo = extractMessageInfo(replyBidForm);
                    System.out.println("Extracted: " + bo);
                    initiateCloseOffer(selectedBid, bo);
                    replyBidForm.dispose();
                } catch (NullPointerException exception) {
                    // TODO: Add error message in UI on incomplete forms, similar to login
                }
            });
            ;}
        );
    };

    public BidInfo extractBidOfferInfo(OfferBid offerBidForm) {
        // offer button is selected -> create offer view -> extract info from view -> create BidInfo -> patch to Bid API
        // close view after offer
        int bidIndexOnDisplay = -1;

        String tutorId = offeringModel.getUserId();
        String time = offerBidForm.getTime();
        String day = offerBidForm.getDay();
        int duration = offerBidForm.getDuration();
        int rate = offerBidForm.getRate();
        int numberOfSessions = offerBidForm.getNumSessions();
        boolean freeLesson = offerBidForm.getFreeLesson();

        BidInfo bidInfo = new BidInfo(tutorId, time, day, duration, rate, numberOfSessions, freeLesson);
        return bidInfo;
    }


    public MessageBidInfo extractMessageInfo(ReplyBid replyBidForm) {

        String tutorId = offeringModel.getUserId();
        String time = replyBidForm.getTime();
        String day = replyBidForm.getDay();
        int duration = replyBidForm.getDuration();
        int rate = replyBidForm.getRate();
        int numberOfSessions = replyBidForm.getNumSessions();
        boolean freeLesson = replyBidForm.getFreeLesson();
        String message = replyBidForm.getReplyMessage();

        MessageBidInfo messageBidInfo = new MessageBidInfo(tutorId, day, time, duration, rate, numberOfSessions, freeLesson, message);
        return messageBidInfo;
    }



    public void initiateOpenOffer(int selectedBid, BidInfo bidInfo){
        offeringModel.sendOffer(selectedBid, bidInfo);
        };

    public void initiateCloseOffer(int selectedBid, MessageBidInfo messageBidInfo){
//        offeringModel.sendMessage(selectedBid, messageInfo);
    };



    public void listenBuyOut() {

    }

    public void listenToReply() {
        // create reply view -> extract info from view -> create BidMessageInfo -> post to Message API
        // view must provide info on which bid did tutor selected
        // close reply view after this function ends

        int bidIndexOnDisplay = -1;

        String tutorId = "-1";
        String time = "-1";
        String day = "Saturday";
        int duration = 1;
        int rate = 10;
        int numberOfSessions = 2;
        boolean freeLesson = true;
        int contractDuration = 4;
        String parsedMessage = "I am a pro tutor";
//        BidInfo bidInfo = new BidInfo(tutorId, time, day, duration, rate, numberOfSessions, contractDuration,
//                parsedMessage, freeLesson);
//
//        offeringModel.sendMessage(bidIndexOnDisplay, bidInfo);
    }



}
