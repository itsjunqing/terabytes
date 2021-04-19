package controller;

import model.BidInfo;
import model.BiddingModel;
import model.ContractModel;
import stream.Bid;
import stream.Contract;
import stream.Lesson;
import stream.Payment;
import view.BiddingView;
import view.ContractView;

import java.util.Calendar;
import java.util.Date;

public class BiddingController {
    private BiddingModel biddingModel;
    private BiddingView biddingView;

    public BiddingController(BiddingModel biddingModel, BiddingView biddingView) {
        this.biddingModel = biddingModel;
        this.biddingView = biddingView;
    }

    public void listenRefresh() {
        // add refresh listener here
        biddingModel.refreshBid();
    }

    public void listenSelectOffer() {
        // add select offer listener here
        int bidOfferId = 0; // replace bidOfferId with which button does the offer correspond to
        biddingModel.markBidClose(); // mark bid as closed
        Bid bidInitiated = biddingModel.getBid();
        BidInfo bidSelected = biddingModel.getBidInfos().get(bidOfferId);

        // Construct Contract
        String studentId = bidInitiated.getInitiatorId();
        String tutorId = String.valueOf(bidSelected.getInitiatorId()); // only tutor can create BidInfo to be added into BiddingModel
        String subjectId = bidInitiated.getSubjectId();
        Date dateCreated = new Date();

        Calendar c = Calendar.getInstance();
        c.setTime(dateCreated);
        c.add(Calendar.DATE, bidSelected.getContractDuration() * 7); // convert x weeks to days
        Date expiryDate = c.getTime();

        Payment payment = new Payment(calculateTotalPrice(bidSelected));
        Lesson lesson = new Lesson(bidSelected.getTime(), bidSelected.getDay(), bidSelected.getDuration(),
                bidSelected.getNumberOfSessions(), bidSelected.isFreeLesson());
//
        Contract contract = new Contract(studentId, tutorId, subjectId, dateCreated, expiryDate, payment, lesson, null);

        createContract(contract);
    }

    private int calculateTotalPrice(BidInfo bidInfo) {
        return bidInfo.getRate() * bidInfo.getNumberOfSessions() * bidInfo.getContractDuration();
    }

    private void createContract(Contract contract) {
        ContractView contractView = new ContractView();
        ContractModel contractModel = new ContractModel();
        ContractController contractController = new ContractController(contractModel, contractView);


    }

}
