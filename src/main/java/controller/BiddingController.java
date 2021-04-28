package controller;

import model.BidInfo;
import model.BiddingModel;
import model.ContractModel;
import stream.Contract;
import view.BiddingView;

//import view.BiddingView;
//import view.ContractView;

public class BiddingController {
    private BiddingModel biddingModel;
    private BiddingView biddingView;
//
    public BiddingController(BiddingModel biddingModel, BiddingView biddingView) {
        this.biddingModel = biddingModel;
        this.biddingView = biddingView;
    }

    public void listenRefresh() {
        // add refresh listener here
        biddingModel.refresh();
    }

    public void listenSelectOffer() {
        // add select offer listener here
//        int bidOfferId = 0; // replace bidOfferId with which button does the offer correspond to
//        biddingModel.markBidClose(); // mark bid as closed
//        Bid bidInitiated = biddingModel.getBid();
//        BidInfo bidSelected = biddingModel.getBidOffers().get(bidOfferId);
//
//        // Construct Contract
//        String studentId = bidInitiated.getInitiatorId();
//        String tutorId = String.valueOf(bidSelected.getInitiatorId()); // only tutor can create BidInfo to be added into BiddingModel
//        String subjectId = bidInitiated.getSubjectId();
//        Date dateCreated = new Date();
//
//        Calendar c = Calendar.getInstance();
//        c.setTime(dateCreated);
//        c.add(Calendar.DATE, bidSelected.getContractDuration() * 7); // convert x weeks to days
//        Date expiryDate = c.getTime();
//
//        Payment payment = new Payment(calculateTotalPrice(bidSelected));
//        Lesson lesson = new Lesson(bidSelected, bidSelected.getDay(), bidSelected.getTime(), bidSelected.getDuration(),
//                bidSelected.getNumberOfSessions(), bidSelected.isFreeLesson());
////
//        Contract contract = new Contract(studentId, tutorId, subjectId, dateCreated, expiryDate, payment, lesson, null);
//
//        createContract(contract);
    }

    private int calculateTotalPrice(BidInfo bidInfo) {
//        return bidInfo.getRate() * bidInfo.getNumberOfSessions() * bidInfo.getContractDuration();
        return 0;
    }

    private void createContract(Contract contract) {
//        ContractView contractView = new ContractView();
        ContractModel contractModel = new ContractModel();
//        ContractController contractController = new ContractController(contractModel, contractView);
//        contractController.createContract(contract);

    }

}
