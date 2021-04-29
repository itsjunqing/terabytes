package engine;

import api.BidApi;
import api.ContractApi;
import api.MessageApi;
import entity.BidInfo;
import entity.BidPreference;
import entity.QualificationTitle;
import stream.*;

import java.util.Calendar;
import java.util.Date;

public class ApiDataAdd {

    public static void addContract1() {
        BidApi bidApi = new BidApi();
        ContractApi contractApi = new ContractApi();
        Calendar c = Calendar.getInstance();

        // Warning, don't run this as it changes the actual data
        // ayyy100 initiates
        String initiatorId = "1ed84243-50ac-437e-872e-39dbce04c5a4";
        String day = "Saturday";
        String time = "2:00PM";
        int duration = 2;
        int rate = 15;
        int sessions = 12;

        BidInfo bidInfo = new BidInfo(initiatorId, day, time, duration, rate, sessions);
        BidPreference bp = new BidPreference(QualificationTitle.CERTIFICATE, 5, "Music", bidInfo);
        BidAdditionalInfo bi = new BidAdditionalInfo(bp);
        Bid bid = new Bid("Open", initiatorId, new Date(), "88f6ee80-4e7b-49b2-847b-23612d8a6f2f", bi);
        Bid bidAdded = bidApi.addBid(bid); // POST TO API
        String bidId = bidAdded.getId();

        // 2 offers: offered by (1) jamesli42, (2) freddiem, choose (2) offer
        // first offer: by jamesli42
        BidInfo offer1 = new BidInfo("b1e0f080-0a8d-4ab0-9c8c-39a607cd5bc9", "Friday", "2:00PM", 2, 20, 10, false);
        bi.getBidOffers().add(offer1); // add into offer
        bidApi.patchBid(bidId, new Bid(bi)); // PATCH TO API, UPDATE BID

        // second offer: by freddiem
        BidInfo offer2 = new BidInfo("9bf9d775-8c70-4b26-ad1c-4120c2abf446", "Saturday", "3:00PM", 2, 20, 10, true);
        bi.getBidOffers().add(offer2);
        bidApi.patchBid(bidId, new Bid(bi)); // PATCH TO API, UPDATE BID
        bidApi.closeBid(bidId, new Bid(new Date())); // MARK BID CLOSED

        // second offer selected, post info to contract
        Payment payment = new Payment(10*20); // second offer: 10 weeks each $20
        Lesson lesson = new Lesson("Music", offer2.getDay(), offer2.getTime(), offer2.getDuration(), offer2.getNumberOfSessions(), offer2.isFreeLesson());
        c.setTime(new Date());
        c.add(Calendar.WEEK_OF_YEAR, 10); // 10 weeks
        Contract contract = new Contract(initiatorId, offer2.getInitiatorId(), "88f6ee80-4e7b-49b2-847b-23612d8a6f2f", new Date(),
                c.getTime(), payment, lesson, new EmptyClass());
        contractApi.patchContract("3130be93-7d06-428c-be74-293e9f3b36ce", contract); // PATCH TO EXISTING CONTRACT

    }

    public static void addContract2() {
        BidApi bidApi = new BidApi();
        ContractApi contractApi = new ContractApi();
        MessageApi messageApi = new MessageApi();
        Calendar c = Calendar.getInstance();

        // Warning, don't run this as it changes the actual data
        // ayyy100 initiates
        String initiatorId = "1ed84243-50ac-437e-872e-39dbce04c5a4";
        String day = "Tuesday";
        String time = "3:00PM";
        int duration = 3;
        int rate = 10;
        int sessions = 4;

        BidInfo bidInfo = new BidInfo(initiatorId, day, time, duration, rate, sessions);
        BidPreference bp = new BidPreference(QualificationTitle.BACHELOR, 5, "Information Technology", bidInfo);
        BidAdditionalInfo bi = new BidAdditionalInfo(bp);
        Bid bid = new Bid("Close", initiatorId, new Date(), "e90fd491-1c3b-41fc-84b7-e1f4ddbed7ba", bi);
        Bid bidAdded = bidApi.addBid(bid); // POST TO API
        String bidId = bidAdded.getId();
        System.out.println(bidId);

        // 3 offers: offered by (1) awesometutor, (2) danto87, (3) iamthewei choose (2) offer
        // first offer: by awesometutor
        MessageAdditionalInfo info = new MessageAdditionalInfo(initiatorId, "Tuesday", "5:00PM", 2, 20, 10, true); // send to initiator
        Message message = new Message(bidId, "9e3d2d23-61ee-460c-9b5d-d9882f0acb9e", new Date(), "I can offer IT to you", info);
        messageApi.addMessage(message);


        // second offer: by danto87
        MessageAdditionalInfo info2 = new MessageAdditionalInfo(initiatorId, "Tuesday", "1:00PM", 2, 20, 10, true);
        Message message2 = new Message(bidId, "4ad8f1ed-4883-4c44-a9ab-a50bdee96ff9", new Date(), "I can offer same day, 2 hours before with free lesson", info2);
        messageApi.addMessage(message2);


        // reply from initiator to danto
        MessageAdditionalInfo replyInfo = new MessageAdditionalInfo("4ad8f1ed-4883-4c44-a9ab-a50bdee96ff9");
        Message replyMsg = new Message(bidId, initiatorId, new Date(), "I like that offer actually", replyInfo);
        messageApi.addMessage(replyMsg);


        // third offer: by iamthewei
        MessageAdditionalInfo info3 = new MessageAdditionalInfo(initiatorId, "Wednesday", "8:00AM", 2, 15, 5, true);
        Message message3 = new Message(bidId, "984e7871-ed81-4f75-9524-3d1870788b1f", new Date(), "I can offer you on morning with free lesson", info3);
        messageApi.addMessage(message3);

        bidApi.closeBid(bidId, new Bid(new Date())); // mark bid as closed

        // Selection of second offer, post info to contract
        Payment payment = new Payment(info2.getRate() * info2.getNumberOfSessions());
        Lesson lesson = new Lesson(bp.getSubject(), info2.getDay(), info2.getTime(), info2.getDuration(), info2.getNumberOfSessions(), info2.getFreeLesson());
        c.setTime(new Date());
        c.add(Calendar.WEEK_OF_YEAR, info2.getNumberOfSessions());

        Contract contract = new Contract(initiatorId, message2.getPosterId(), "e90fd491-1c3b-41fc-84b7-e1f4ddbed7ba", new Date(),
                c.getTime(), payment, lesson, new EmptyClass());
        contractApi.patchContract("d8890c50-6480-48db-8497-433b5ade22a2", contract); // PATCH TO EXISTING CONTRACT


    }

    public static void addContract3() {
        BidApi bidApi = new BidApi();
        ContractApi contractApi = new ContractApi();
        Calendar c = Calendar.getInstance();

        // ronlow initiates
        String initiatorId = "2f8179cb-8325-4760-98ef-5b3f781ad596";
        String day = "Wednesday";
        String time = "2:00PM";
        int duration = 2;
        int rate = 5;
        int sessions = 8;

        BidInfo bidInfo = new BidInfo(initiatorId, day, time, duration, rate, sessions);
        BidPreference bp = new BidPreference(QualificationTitle.CERTIFICATE, 3, "History", bidInfo);
        BidAdditionalInfo bi = new BidAdditionalInfo(bp);
        Bid bid = new Bid("Open", initiatorId, new Date(), "841199ac-d73e-4726-888d-dfeb538f49e2", bi);
        Bid bidAdded = bidApi.addBid(bid); // POST TO API
        String bidId = bidAdded.getId();

        // 1 offer only: by neilly (only person to have History), instant buy out by neilly, does not have free lesson by default (Buy out)
        BidInfo offer = new BidInfo("3e541287-2ea2-4dad-b729-761d8f36059f", day, time, duration, rate, sessions, false);
        bi.getBidOffers().add(offer);
        bidApi.patchBid(bidId, new Bid(bi));

        bidApi.closeBid(bidId, new Bid(new Date())); // MARK BID CLOSED

        Payment payment = new Payment(bidInfo.getRate() * bidInfo.getNumberOfSessions());
        Lesson lesson = new Lesson("History", day, time, duration, sessions, false);
        c.setTime(new Date());
        c.add(Calendar.WEEK_OF_YEAR, sessions);
        Contract contract = new Contract(initiatorId, "3e541287-2ea2-4dad-b729-761d8f36059f", bid.getSubjectId(), new Date(),
                c.getTime(), payment, lesson, new EmptyClass());
        contractApi.patchContract("cfcab584-6620-4d31-a709-8bfcc118b3f8", contract); // PATCH TO EXISTING CONTRACT


    }

    public static void addContract4() {
        BidApi bidApi = new BidApi();
        ContractApi contractApi = new ContractApi();
        Calendar c = Calendar.getInstance();

        // ronlow initiates
        String initiatorId = "2f8179cb-8325-4760-98ef-5b3f781ad596";
        String day = "Friday";
        String time = "4:00PM";
        int duration = 1;
        int rate = 20;
        int sessions = 8; // 8 weeks

        BidInfo bidInfo = new BidInfo(initiatorId, day, time, duration, rate, sessions);
        BidPreference bp = new BidPreference(QualificationTitle.DIPLOMA, 2, "Painting", bidInfo);
        BidAdditionalInfo bi = new BidAdditionalInfo(bp);
        Bid bid = new Bid("Open", initiatorId, new Date(), "47ebe20c-a752-470c-aedd-73cfe52efa4f", bi);
        Bid bidAdded = bidApi.addBid(bid); // POST TO API
        String bidId = bidAdded.getId();

        // 1 offer only: by neilly (only person to have Painting), instant buy out by neilly, does not have free lesson by default (Buy out)
        BidInfo offer = new BidInfo("3e541287-2ea2-4dad-b729-761d8f36059f", day, time, duration, rate, sessions, false);
        bi.getBidOffers().add(offer);
        bidApi.patchBid(bidId, new Bid(bi));

        bidApi.closeBid(bidId, new Bid(new Date())); // MARK BID CLOSED

        Payment payment = new Payment(bidInfo.getRate() * bidInfo.getNumberOfSessions());
        Lesson lesson = new Lesson("Painting", day, time, duration, sessions, false);
        c.setTime(new Date());
        c.add(Calendar.WEEK_OF_YEAR, sessions);
        Contract contract = new Contract(initiatorId, "3e541287-2ea2-4dad-b729-761d8f36059f", bid.getSubjectId(), new Date(),
                c.getTime(), payment, lesson, new EmptyClass());
        contractApi.patchContract("a5160905-c273-43f9-b83b-dd79e8d4f922", contract); // PATCH TO EXISTING CONTRACT


    }
}
