package engine;

import Test.ContractViewTest;
import api.BidApi;
import api.ContractApi;
import controller.LoginController;
import entity.BidInfo;
import model.LoginModel;
import stream.*;
import view.LoginView;
import view.form.BidInitiation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.OffsetDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class Driver {
    public static void main( String[] args ) {
        if (System.getenv("API_KEY") == null) {
            Logger.getLogger(Driver.class.getName()).info("Please set API_KEY as environment variable");
            return;
        }

//        List<String> someInt = new ArrayList<String>();
//        Collections.reverse(someInt);

         //Login model test
        LoginModel loginModel = new LoginModel();
        LoginView loginView = new LoginView();
        LoginController loginController = new LoginController(loginModel, loginView);

        ContractApi contractApi = new ContractApi();
        List<Contract> contractList = contractApi.getAllContracts();
        Contract contract1 = contractList.get(0);
        Contract contract2 = contractList.get(2);
        Contract contract3 = contractList.get(3);
        Contract contract4 = contractList.get(4);

        ContractViewTest contractViewTest = new ContractViewTest(contract1);
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
        contractViewTest.update(contract2);
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }

        contractViewTest.update(contract3);
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
        contractViewTest.update(contract4);



//        BidApi bidApi = new BidApi();
//
//        List<Bid> allBids = bidApi.getAllBids();
//
//        Bid bid = allBids.get(0);

//        Date then = bid.getDateCreated();
//
//        Date now = new Date();
//        long difference = now.getTime() - then.getTime();
//        long minuteDifference = TimeUnit.MILLISECONDS.toMinutes(difference);
//        long dayDifference = TimeUnit.MILLISECONDS.toDays(difference);
//        if (minuteDifference > 30){
//            System.out.println("over 30 minutes");
//        }
//        if (dayDifference > 7){
//            System.out.println("over one week");
//        }

//        TimeZone tz = TimeZone.getTimeZone("UTC");
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
//        df.setTimeZone(tz);
//        String nowAsISO = df.format(new Date());
//        System.out.println(nowAsISO);
//        Date date = new Date();
//        System.out.println(date);
//        Calendar c = Calendar.getInstance();
//        c.setTime(date);
//        c.add(Calendar.DATE, 14);
//        Date d = c.getTimeBox();
//        System.out.println(d);





//        testOfferingModel.setBidsOnGoing();
//        BidApi bidApi = new BidApi();
//        List<Bid> bidList = bidApi.getAllBids();

//        testOfferingModel.refresh();
        // Passing them a list of bids to test. Once the api is complete, will not pass bids

//        // create new bid for testing, complete with offers
//        // add open and closed bids
////        testOfferingModel.setBidsOnGoing();
//        BidApi bidApi = new BidApi();
//        List<Bid> bidList = bidApi.getAllBids();
//
////        testOfferingModel.refresh();
//        // Passing them a list of bids to test. Once the api is complete, will not pass bids

//        ActiveRequestsView activeRequestViewTest = new ActiveRequestsView(testOfferingModel, bidList);
//        activeRequestViewTest.displayContracts();
//        sampleUsageStudentBid();
//        sampleUsageOffering();
//        sampleUsageOffering2();

    }
    // true if expired, false otherwise
    public static boolean isExpired(Bid bid){
        Date then = bid.getDateCreated();
        Date now = new Date();
        long difference = now.getTime() - then.getTime();
        long minuteDifference = TimeUnit.MILLISECONDS.toMinutes(difference);
        long dayDifference = TimeUnit.MILLISECONDS.toDays(difference);
        if (bid.getType().equals("Open") ) {
            return minuteDifference > 30;
        } else {
                return dayDifference > 7;
        }
    }
    // true if has offer, false otherwise
    public static boolean hasOffer(Bid bid) {
        return bid.getAdditionalInfo().getBidOffers().size() != 0;
    }
    public static void checkExpired(Bid bid){
        BidApi bidApi = new BidApi();
        ContractApi contractApi = new ContractApi();
        // if not closed
        // TODO: change this
        if (bid.getDateClosedDown() != null) {
            System.out.println("Bid is not closed down");
            // if open
            if (bid.getType().equals("Open")) {
                System.out.println("Bid is Open bid");
                // if is expired
                if (isExpired(bid)) {
                    System.out.println("Bid expired");
                    // if has offer, get latest offer
                    if (hasOffer(bid)) {
                        System.out.println("Bid has offer");
                        // close the bid
                        bidApi.closeBid(bid.getId(), bid);
                        // get last bid
                        BidInfo bidInfo = bid.getAdditionalInfo().getBidOffers().get(bid.getAdditionalInfo().getBidOffers().size() - 1);
                        // create contract
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
                        contractApi.addContract(contract);
                    }
                    // if no offer, close
                    else {
                        System.out.println("Bid has no offer");
                        bidApi.closeBid(bid.getId(), bid);
                    }
                }// if not expired pass
            }
            // if close
            else {
                System.out.println("Bid is a closed bid");
                // if is expired, close bid
                if (isExpired(bid)) {
                    System.out.println("Bid is expired");
                    bidApi.closeBid(bid.getId(), bid);
                    // close bid
                }
                // if not expired pass
            }
        }
    }


    private static void sampleUsageStudentBid() {
//        // TODO: for Nick to run to see, this is to be displayed in the view
////        // TODO: for Nick to run to see, this is to be displayed in the view
//        OpenBidModel openBidModel = new OpenBidModel();
//        openBidModel.setBidId("c9b04eee-6a57-4b1f-ba06-7c1f7c2e87b3");
//        openBidModel.setUserId("1ed84243-50ac-437e-872e-39dbce04c5a4");
//        openBidModel.refresh();
//        OpenBidView openBidView = new OpenBidView(openBidModel);

////        openBidModel.getOpenBidOffers().stream().forEach(b -> System.out.println(b));
//
//
//        // TODO: For Nick to run to see, we gonna use this to manipulate the view
//
//////        openBidModel.getOpenBidOffers().stream().forEach(b -> System.out.println(b));
////
////
////        // TODO: For Nick to run to see, we gonna use this to manipulate the view
//        CloseBidModel closeBidModel = new CloseBidModel();
//        closeBidModel.setUserId("1ed84243-50ac-437e-872e-39dbce04c5a4");
//        closeBidModel.setBidId("51ab43a7-25aa-4ff2-a052-418e5a46b774");
//        closeBidModel.refresh();
//        CloseBidView closeBidView = new CloseBidView(closeBidModel);
//
//        closeBidModel.getCloseBidOffers().stream().forEach(m -> System.out.println(m));
//        closeBidModel.getCloseBidMessages().stream().forEach(m -> System.out.println(m));
//        List<MessagePair> messagePairs= closeBidModel.getCloseBidMessages();
//        MessagePair sampleMessagePair = messagePairs.get(0);
//
        int selectedBid = 1;
////        CloseBidView closeBidView = new CloseBidView(closeBidModel);
////
////        closeBidModel.getCloseBidOffers().stream().forEach(m -> System.out.println(m));
////        closeBidModel.getCloseBidMessages().stream().forEach(m -> System.out.println(m));
////        List<MessagePair> messagePairs= closeBidModel.getCloseBidMessages();
////        MessagePair sampleMessagePair = messagePairs.get(0);
////
//        int selectedBid = 1;
//        CloseMessageView closeMessageView = new CloseMessageView(closeBidModel, selectedBid);
    }
//
//    private static void sampleUsageOffering() {
//        /**
//         * this function shows for user: jamesli42
//         * 1. able to see all open offers of a bid
//         * 2. unable to see close offer of a bid (as it has not offered to the student yet)
//         */
//
//        // TODO: For nick to run to see, this is to be displayed in OfferingView for the list of bids currently active
////        OfferingModel offeringModel = new OfferingModel("b1e0f080-0a8d-4ab0-9c8c-39a607cd5bc9");
////        offeringModel.refresh();
//////        OfferingView offeringView = new OfferingView(offeringModel);
////
////        System.out.println(">> ALL BIDS ON GOING ARE: ");
////        List<Bid> ongoing = offeringModel.getBidsOnGoing();
////        ongoing.stream().forEach(b -> System.out.println(b));
//
////
////        System.out.println((bid.getInitiator().getGivenName()));
////        System.out.println(bid.getSubject().getName());
////        System.out.println(bid.getAdditionalInfo().getBidPreference().getPreferences().getNumberOfSessions());
//
//
//
////
////        // TODO: this is to be displayed in Offering View, when tutor selects ID and view its offers, then check open or close
//        int selectedBid = 2; // second is open bid
////        Bid selected = ongoing.get(selectedBid-1);
////        if (selected.getType().equals("Open")) {
//////            System.out.println(">> OPEN BIDS ON GOING ARE: ");
//////            selected
////        }
////        OpenOffersView openOffersView = new OpenOffersView(offeringModel, selectedBid);
//////
////        int selectedBid = 1; // first is close bid, tutor selects number 1
////        Bid selected = ongoing.get(selectedBid-1);
////        if (selected.getType().equals("Close")) {
////            System.out.println(">> CLOSE BIDS ON GOING ARE: ");
////            MessagePair pair = offeringModel.getCloseOffers(selectedBid-1);
////            // TODO: always display student's message, because it will always exist
////            System.out.println(pair.getStudentMsg());
////            // TODO: need to check if the tutor message exist or not
////            if (pair.getTutorMsg() != null) {
////                System.out.println(pair.getTutorMsg());
////            } else {
////                System.out.println("No messages sent to student yet");
////            }
////
////        }
////        CloseOfferView closeOfferView = new CloseOfferView(offeringModel, selectedBid);
//    }

//    private static void sampleUsageOffering2() {
//        /**
//         * this function shows for user: bigben
//         * 1. able to see all open offers of a bid
//         * 2. able to see the close offer of a bid (as it has provided an offer to the close bid)
//         */
//        // TODO: For nick to run to see, this is to be displayed in OfferingView for the list of bids currently active
//        OfferingModel offeringModel = new OfferingModel("d90f9f94-7603-4231-805c-eb62158ad3c6");
//        offeringModel.refresh();
//        OfferingView offeringView = new OfferingView(offeringModel);
//
////        System.out.println(">> ALL BIDS ON GOING ARE: ");
//        List<Bid> ongoing = offeringModel.getBidsOnGoing();
////        ongoing.stream().forEach(b -> System.out.println(b));
//
//        // TODO: this is to be displayed in Offering View, when tutor selects ID and view its offers, then check open or close
//        int selectedBid = 2; // second is open bid
//        Bid selected = ongoing.get(selectedBid-1);
//        if (selected.getType().equals("Open")) {
//            OpenOffersView openOffersView = new OpenOffersView(offeringModel, 2);
////            System.out.println(">> OPEN BIDS ON GOING ARE: ");
////            offeringModel.getOpenOffers(selectedBid-1).stream().forEach(o -> System.out.println(o));
//
//        }
//
//        selectedBid = 1; // first is close bid, tutor selects number 1
//        selected = ongoing.get(selectedBid-1);
//        if (selected.getType().equals("Close")) {
////            System.out.println(">> CLOSE BIDS ON GOING ARE: ");
////            MessagePair pair = offeringModel.getCloseOffers(selectedBid-1);
////            // TODO: always display student's message, because it will always exist
////            System.out.println(pair.getStudentMsg());
////            // TODO: need to check if the tutor message exist or not
////            if (pair.getTutorMsg() != null) {
////                System.out.println(pair.getTutorMsg());
////            } else {
////                System.out.println("No messages sent to student yet");
////            }
//            CloseOfferView closeOfferView = new CloseOfferView(offeringModel, 1);
//
//        }







    // bid listeners test
    private static void listenBid(BidInitiation bidInitiation){
        bidInitiation.getOpenBidButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(bidInitiation.getSubject());
                System.out.println(bidInitiation.getSubject());
                System.out.println(bidInitiation.getRate());
            }
        });
    }



}