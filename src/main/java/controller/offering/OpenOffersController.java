package controller.offering;

import entity.BidInfo;
import entity.BidPreference;
import model.offering.OpenOffersModel;
import stream.*;
import view.form.OpenReply;
import view.offering.OpenOffersView;

import java.awt.event.ActionEvent;
import java.util.Calendar;
import java.util.Date;

public class OpenOffersController {

    private OpenOffersModel openOffersModel;
    private OpenOffersView openOffersView;

    public OpenOffersController(String userId, String bidId) {
        this.openOffersModel = new OpenOffersModel(userId, bidId);
        this.openOffersView = new OpenOffersView(openOffersModel);
//        openOffersModel.oSubject.attach(openOffersView);
        this.openOffersModel.attach(openOffersView);
        listenViewActions();
    }

    private void listenViewActions() {
        openOffersView.getRefreshButton().addActionListener(this::handleRefresh);
        openOffersView.getRespondButton().addActionListener(this::handleRespond);
        openOffersView.getBuyOutButton().addActionListener(this::handleBuyOut);
    }

    private void handleRefresh(ActionEvent e) {
        openOffersModel.refresh();
        openOffersView.getRefreshButton().addActionListener(this::handleRefresh);
        openOffersView.getRespondButton().addActionListener(this::handleRespond);
        openOffersView.getBuyOutButton().addActionListener(this::handleBuyOut);
    }

    private void handleRespond(ActionEvent e) {
        OpenReply openReply = new OpenReply();
        openReply.getOfferBidButton().addActionListener(e1 -> handleBidInfo(e1, openReply));
    }

    private void handleBidInfo(ActionEvent e, OpenReply openReplyForm) {
        try {
            BidInfo bidInfo = extractOpenReplyInfo(openReplyForm);
            System.out.println("Extracted: " + bidInfo);

            openReplyForm.dispose();

            openOffersModel.sendOffer(bidInfo);

        } catch (NullPointerException exception) {
            // TODO : add error message for incomplete forms
        }
    }

    private void handleBuyOut(ActionEvent e) {
        // Get preferences -> Add BidInfo -> create contract -> sign -> dispose
        // TODO: Nick need to add contract handling here for signing
        BidPreference bp = openOffersModel.getBid().getAdditionalInfo().getBidPreference();
        BidInfo bidInfo = bp.getPreferences();
        bidInfo.setInitiatorId(openOffersModel.getUserId());
        openOffersModel.sendOffer(bidInfo);
        createContract(openOffersModel.getBid(), bidInfo);
        openOffersView.dispose();
    }

    private BidInfo extractOpenReplyInfo(OpenReply openReplyForm) throws NullPointerException {
        String tutorId = openOffersModel.getUserId();
        String time = openReplyForm.getTimeBox();
        String day = openReplyForm.getDayBox();
        int duration = openReplyForm.getDurationBox();
        int rate = openReplyForm.getRateField();
        int numberOfSessions = openReplyForm.getNumOfSessionBox();
        boolean freeLesson = openReplyForm.getFreeLessonBox();
        return new BidInfo(tutorId, time, day, duration, rate, numberOfSessions, freeLesson);
    }

    /**
     * BOTTOM PART IS COPIED FROM BiddingController
     * MAYBE CAN ABSTRACTED OUT
     */
    protected void createContract(Bid bid, BidInfo bidInfo) {
        String studentId = bid.getInitiator().getId();
        String tutorId = bidInfo.getInitiatorId();
        String subjectId = bid.getSubject().getId();
        Date dateCreated = new Date();

        // take currentDate + number of sessions (weeks) to get expiry date
        Calendar c = Calendar.getInstance();
        c.setTime(dateCreated);
        c.add(Calendar.WEEK_OF_YEAR, bidInfo.getNumberOfSessions());
        Date expiryDate = c.getTime();

        // calculate payment = rate per session * number of sessions
        Payment payment = new Payment(bidInfo.getRate() * bidInfo.getNumberOfSessions());
        Lesson lesson = new Lesson(bid.getSubject().getName(), bidInfo.getDay(), bidInfo.getTime(),
                bidInfo.getDuration(), bidInfo.getNumberOfSessions(), bidInfo.isFreeLesson());
        Contract contract = new Contract(studentId, tutorId, subjectId, dateCreated,
                expiryDate, payment, lesson, new EmptyClass());
        handleContract(contract);
    }

    private void handleContract(Contract contract) {
        // TODO: handle Contract pushing + Contract MVC construction
    }

}
