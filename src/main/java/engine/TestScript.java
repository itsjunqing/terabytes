package engine;

import api.BidApi;
import api.ContractApi;
import api.MessageApi;
import entity.BidInfo;
import entity.Preference;
import entity.QualificationTitle;
import entity.Utility;
import service.ApiService;
import stream.*;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestScript {

    /**
     * Example of usage: TestScript.clearAllData()
     * This will clear ALL Message -> ALL Bid -> ALL Contract
     */
    public static void clearAllData() {
        MessageApi messageApi = new MessageApi();
        List<Message> messages = messageApi.getAll();
        messages.stream().forEach(b -> messageApi.remove(b.getId()));

        BidApi bidApi = new BidApi();
        List<Bid> bids = bidApi.getAll();
        bids.stream().forEach(b -> bidApi.remove(b.getId()));

        ContractApi contractApi = new ContractApi();
        List<Contract> contracts = contractApi.getAll();
        contracts.stream().forEach(b -> contractApi.remove(b.getId()));
    }

    /**
     * Example of usage: TestScript.clearAllData("renewalstudent")
     * This will clear ALL Message -> ALL Bid -> ALL Contract of a particular user
     */
    public static void clearData(String username) {
        User user = Utility.getUser(username);
        String userId = user.getId();

        MessageApi messageApi = new MessageApi();
        List<Message> messages = messageApi.getAll();
        messages.stream()
                .filter(m -> m.getPoster().getId().equals(userId))
                .forEach(b -> messageApi.remove(b.getId()));

        BidApi bidApi = new BidApi();
        List<Bid> bids = bidApi.getAll();
        bids.stream()
                .filter(b -> b.getInitiator().getId().equals(userId))
                .forEach(b -> bidApi.remove(b.getId()));

        ContractApi contractApi = new ContractApi();
        List<Contract> contracts = contractApi.getAll();
        contracts.stream()
                .filter(c -> c.getFirstParty().getId().equals(userId)
                        || c.getSecondParty().getId().equals(userId))
                .forEach(b -> contractApi.remove(b.getId()));
    }

    /**
     * Example of usage: TestScript.generatePlainStudent("Dummy", "Student 2", "dummystudent2");
     * This will generate a plain student (no qualifications + competency)
     */
    public static void generatePlainStudent(String fName, String lName, String username) {
        boolean userExist = ApiService.userApi().getAll().stream()
                .anyMatch(u -> u.getUserName().equals(username));
        if (userExist) { // safety check
            return;
        }
        User student = new User(fName, lName, username, username, true, false);
        ApiService.userApi().add(student);
    }

    /**
     * Example of usage: TestScript.generateProTutor("Dummy", "Tutor", "dummytutor", 10);
     * This will generate tutors of all qualifications + all competencies of level 10
     */
    public static void generateProTutor(String fName, String lName, String username, int level) {
        if (level < 0 || level > 10) {
            System.out.println("Please give level between 1 to 10 (inclusive)");
            return;
        }
        boolean userExist = ApiService.userApi().getAll().stream()
                .anyMatch(u -> u.getUserName().equals(username));
        if (userExist) { // safety check
            return;
        }
        User tutor = new User(fName, lName, username, username, false, true);
        User addedTutor = ApiService.userApi().add(tutor);
        String tutorId = addedTutor.getId();

        // add all qualifications
        Arrays.asList(QualificationTitle.values()).stream()
                .map(q -> new Qualification(q.toString(), q.toString() + " in every area", true, tutorId))
                .forEach(q -> ApiService.qualificationApi().add(q));

        // add all competency of max level
        ApiService.subjectApi().getAll().stream()
                .map(s -> new Competency(tutorId, s.getId(), level))
                .forEach(c -> ApiService.competencyApi().add(c));

    }

    /**
     * Example of usage: TestScript.generateAboutToExpireContracts("expirystudent", "dummytutor");
     * This creates contracts that are about to expire for the student "expirystudent".
     * Used to perform testing on ExpiryNotification
     */
    public static void generateAboutToExpireContracts(String studentUsername, String tutorUsername) {
        User student = ApiService.userApi().getAll().stream()
                .filter(u -> u.getUserName().equals(studentUsername))
                .findFirst()
                .orElse(null);
        if (student == null || !student.getIsStudent()) { // safety check
            System.out.println("Student doesn't exist or it is not a student, pls check parameters or create first");
            return;
        }

        User tutor = ApiService.userApi().getAll().stream()
                .filter(u -> u.getUserName().equals(tutorUsername))
                .findFirst()
                .orElse(null);
        if (tutor == null || !tutor.getIsTutor()) { // safety check
            System.out.println("Tutor doesn't exist or it is not a tutor, pls check parameters or create first");
            return;
        }

        Calendar c = Calendar.getInstance();
        Date today = new Date();

        // Generate 1 contract that is exactly 1 month from today's date - add 1 month
        c.setTime(today);
        c.add(Calendar.MONTH, 1);
        Date exactly1Month = c.getTime();

        // Generate 1 contract that is less than 1 month from today's date - add 29 days
        c.setTime(today);
        c.add(Calendar.DAY_OF_YEAR, 29);
        Date less1Month = c.getTime();

        // Generate 1 contract that is more than 1 month from today's date - add 32 days
        c.setTime(today);
        c.add(Calendar.DAY_OF_YEAR, 32);
        Date more1Month = c.getTime();

        // Testing expiry date of exactly 1 month has same day as today
        c.setTime(exactly1Month);
        c.add(Calendar.MONTH, -1);
        assert Utility.isSameDay(c.getTime(), today);

        // Testing expiry date of 1 month before is before today (within 1 month)
        c.setTime(less1Month);
        c.add(Calendar.MONTH, -1);
        assert c.getTime().before(today);

        // Testing expiry date of 1 month before is after today (beyond 1 month)
        c.setTime(more1Month);
        c.add(Calendar.MONTH, -1);
        assert c.getTime().after(today);

        // Create general contract
        String frenchSubjectId = "e514c1e0-7510-46d6-a582-395be7f4dc76";
        Payment defaultPayment = new Payment(100);
        Lesson defaultLesson = new Lesson("French", "Friday", "8:00AM", 2, 3, false);
        BidInfo defaultBidInfo = new BidInfo(student.getId(), "Friday", "8:00AM", 2, 13, 3);
        Preference defaultPreference = new Preference(QualificationTitle.PHD, 3, "French", defaultBidInfo);
        Contract contract = new Contract(student.getId(), tutor.getId(), frenchSubjectId, today, exactly1Month,
                defaultPayment, defaultLesson, defaultPreference);

        // Update for exactly1Month and post to API
        contract.setExpiryDate(exactly1Month);
        Contract pushedExact = ApiService.contractApi().add(contract);

        // Update for less1Month and post to API
        contract.setExpiryDate(less1Month);
        Contract pushedLess = ApiService.contractApi().add(contract);

        // Update for more1Month and post to API
        contract.setExpiryDate(more1Month);
        Contract pushedMore = ApiService.contractApi().add(contract);

        // Sign after 5 seconds, because sign time must be > current time
        try {
            TimeUnit.SECONDS.sleep(5);
            Date signDate = new Date();
            Contract signCon = new Contract(signDate);

            ApiService.contractApi().sign(pushedExact.getId(), signCon);
            ApiService.contractApi().sign(pushedLess.getId(), signCon);
            ApiService.contractApi().sign(pushedMore.getId(), signCon);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

    }

    /**
     * Example of usage: TestScript.generateExpiredContracts("renewalstudent", "dummytutor", 3);
     * Creates a Contract between "renewalstudent" and "dummytutor" whose subject competency preference is 3
     * When level = 3 is provided, upon renewal, dummytutor, dummytutor1, dummytutor2, dummytutor3 should appear in tutor selection
     * When level = 4 is provided, upon renewal, dummytutor, dummytutor1 should appear in tutor selection
     *      (dummytutor2 and dummytutor3 doesn't have requirement of at least >= 5 (3+2) )
     * Used to perform testing on ContractRenewal by Student
     */
    public static void generateExpiredContracts(String studentUsername, String tutorUsername, int level) {
        if (level != 3 && level != 4) { // safety check
            System.out.println("Provide only level = 3 or level = 4");
            return;
        }
        User student = ApiService.userApi().getAll().stream()
                .filter(u -> u.getUserName().equals(studentUsername))
                .findFirst()
                .orElse(null);
        if (student == null || !student.getIsStudent()) { // safety check
            System.out.println("Student doesn't exist or it is not a student, pls check parameters or create first");
            return;
        }

        User tutor = ApiService.userApi().getAll().stream()
                .filter(u -> u.getUserName().equals(tutorUsername))
                .findFirst()
                .orElse(null);
        if (tutor == null || !tutor.getIsTutor()) { // safety check
            System.out.println("Tutor doesn't exist or it is not a tutor, pls check parameters or create first");
            return;
        }

        Calendar c = Calendar.getInstance();
        Date today = new Date();

        // Generate 1 contract that is expired 10 days before today - subtract 10 days from today
        c.setTime(today);
        c.add(Calendar.DAY_OF_YEAR, -10);
        Date expired10DaysAgo = c.getTime();

        // Generate 1 contract that is expired 1 day before today - subtract 1 day from today
        c.setTime(today);
        c.add(Calendar.DAY_OF_YEAR, -1);
        Date expired1DayAgo = c.getTime();

        // Generate 1 contract that is expired 10 seconds before today (now) - subtract 10 seconds from today
        c.setTime(today);
        c.add(Calendar.SECOND, -10);
        Date expired10SecAgo = c.getTime();

        // Test if expired before today
        assert expired10DaysAgo.before(today);
        assert expired1DayAgo.before(today);
        assert expired10SecAgo.before(today);

        // Get creation date because creation date must be before expiry date
        c.set(2021, Calendar.MARCH, 1);
        Date creationDate = c.getTime();

        // general usage
        Payment defaultPayment = new Payment(100);
        BidInfo defaultBidInfo = new BidInfo(student.getId(), "Monday", "2:00PM", 2, 13, 5);
        Preference defaultPreference = new Preference(QualificationTitle.PHD, level, "Chemistry", defaultBidInfo);

        /* ====================================================
        MODIFICATION STARTS HERE
         ==================================================== */

        // Create contract for expired10DaysAgo
        String chemSubjectId = "acd0bfef-8aa7-4b1b-8716-95124b397766";
        Lesson chemLesson = new Lesson("Chemistry", "Monday", "2:00PM", 2, 3, false);
        defaultPreference.setSubject("Chemistry");
        defaultPayment.setTotalPrice(60);
        Contract chemContract = new Contract(student.getId(), tutor.getId(), chemSubjectId, creationDate, expired10DaysAgo,
                defaultPayment, chemLesson, defaultPreference);
        // Update for expired10DaysAgo and post to API
        Contract pushed10DaysAgo = ApiService.contractApi().add(chemContract);

        // Create contract for expired1DayAgo
        String paintingSubId = "47ebe20c-a752-470c-aedd-73cfe52efa4f";
        Lesson paintingLesson = new Lesson("Painting", "Friday", "5:00PM", 3, 5, false);
        defaultPreference.setSubject("Painting");
        defaultPayment.setTotalPrice(70);
        Contract paintingContract = new Contract(student.getId(), tutor.getId(), paintingSubId, creationDate, expired1DayAgo,
                defaultPayment, paintingLesson, defaultPreference);
        // Update for expired1DayAgo and post to API
        Contract pushed1DayAgo = ApiService.contractApi().add(paintingContract);


        // Create contract for expired1DayAgo
        String englishSubId = "aff328c9-2da2-47ae-a2c5-1acc06f6f7eb";
        Lesson englishLes = new Lesson("English", "Wednesday", "3:00PM", 2, 4, false);
        defaultPreference.setSubject("English");
        defaultPayment.setTotalPrice(96);
        Contract englishContract = new Contract(student.getId(), tutor.getId(), englishSubId, creationDate, expired10SecAgo,
                defaultPayment, englishLes, defaultPreference);
        // Update for expired10SecAgo and post to API
        Contract pushed10SecAgo = ApiService.contractApi().add(englishContract);

        // Sign 5 seconds after creation date, because sign time must be > creation time
        c.setTime(creationDate);
        c.add(Calendar.SECOND, 5);
        Date signDate = c.getTime();
        Contract signCon = new Contract(signDate);

        ApiService.contractApi().sign(pushed10DaysAgo.getId(), signCon);
        ApiService.contractApi().sign(pushed1DayAgo.getId(), signCon);
        ApiService.contractApi().sign(pushed10SecAgo.getId(), signCon);


    }

    /**
     * Example of usage: TestScript.generateActiveContracts("renewalstudent", "dummytutor")
     * This creates 5 active contracts between renewalstudent and dummytutor
     * Pls call TestScript.generateExpiredContracts("renewalstudent", "dummytutor", 3) manually to add the expired contracts
     *  This will create 5 active contracts + 3 expired contracts = preventing renewal (5 has reached)
     */
    public static void generateActiveContracts(String studentUsername, String tutorUsername) {
        User student = ApiService.userApi().getAll().stream()
                .filter(u -> u.getUserName().equals(studentUsername))
                .findFirst()
                .orElse(null);
        if (student == null || !student.getIsStudent()) { // safety check
            System.out.println("Student doesn't exist or it is not a student, pls check parameters or create first");
            return;
        }

        User tutor = ApiService.userApi().getAll().stream()
                .filter(u -> u.getUserName().equals(tutorUsername))
                .findFirst()
                .orElse(null);
        if (tutor == null || !tutor.getIsTutor()) { // safety check
            System.out.println("Tutor doesn't exist or it is not a tutor, pls check parameters or create first");
            return;
        }

        Calendar c = Calendar.getInstance();
        Date today = new Date();

        // Get the time of the same expiry date (default to 6 months for all)
        c.setTime(today);
        c.add(Calendar.MONTH, 6);
        Date expiryDate = c.getTime();

        // general usage
        Payment defaultPayment = new Payment(100);
        BidInfo defaultBidInfo = new BidInfo(student.getId(), "Monday", "2:00PM", 2, 13, 5);
        Preference defaultPreference = new Preference(QualificationTitle.PHD, 3, "Chemistry", defaultBidInfo);

        /* ====================================================
        MODIFICATION STARTS HERE
         ==================================================== */

        // Create 1 contract for Physics
        String physicsId = "148e0af0-699b-4c1f-9e49-4de8816d121e";
        Lesson phyLes = new Lesson("Physics", "Monday", "3:00PM", 1, 5, false);
        defaultPreference.setSubject("Physics");
        defaultPayment.setTotalPrice(50);
        Contract phyContract = new Contract(student.getId(), tutor.getId(), physicsId, today, expiryDate,
                defaultPayment, phyLes, defaultPreference);
        Contract pushedPhy = ApiService.contractApi().add(phyContract);

        // Create 1 contract for Mathematics
        String mathId = "8a921487-859f-4931-8743-f69c38f91b25";
        Lesson mathLes = new Lesson("Mathematics", "Tuesday", "5:00PM", 3, 4, true);
        defaultPreference.setSubject("Mathematics");
        defaultPayment.setTotalPrice(78);
        Contract mathContract = new Contract(student.getId(), tutor.getId(), mathId, today, expiryDate,
                defaultPayment, mathLes, defaultPreference);
        Contract pushedMath = ApiService.contractApi().add(mathContract);

        // Create 1 contract for History
        String historyId = "841199ac-d73e-4726-888d-dfeb538f49e2";
        Lesson hisLes = new Lesson("History", "Friday", "6:00PM", 3, 2, false);
        defaultPreference.setSubject("History");
        defaultPayment.setTotalPrice(83);
        Contract histContract = new Contract(student.getId(), tutor.getId(), historyId, today, expiryDate,
                defaultPayment, hisLes, defaultPreference);
        Contract pushedHist = ApiService.contractApi().add(histContract);

        // Create 1 contract for Accounting
        String accountId = "d89f2232-6ae9-4d79-b018-a088ab55bddb";
        Lesson accountLes = new Lesson("Accounting", "Thursday", "11:00AM", 2, 4, false);
        defaultPreference.setSubject("Accounting");
        defaultPayment.setTotalPrice(120);
        Contract accountContract = new Contract(student.getId(), tutor.getId(), accountId, today, expiryDate,
                defaultPayment, accountLes, defaultPreference);
        Contract pushedAcc = ApiService.contractApi().add(accountContract);

        // Create 1 contract for Music
        String musId = "88f6ee80-4e7b-49b2-847b-23612d8a6f2f";
        Lesson musLes = new Lesson("Music", "Tuesday", "2:00PM", 1, 3, true);
        defaultPreference.setSubject("Music");
        defaultPayment.setTotalPrice(132);
        Contract musContract = new Contract(student.getId(), tutor.getId(), musId, today, expiryDate,
                defaultPayment, musLes, defaultPreference);
        Contract pushedMus = ApiService.contractApi().add(musContract);

        // Sign 5 seconds after creation date, because sign time must be > creation time
        c.setTime(today);
        c.add(Calendar.SECOND, 5);
        Date signDate = c.getTime();
        Contract signCon = new Contract(signDate);

        // Sign all the contracts
        ApiService.contractApi().sign(pushedPhy.getId(), signCon);
        ApiService.contractApi().sign(pushedMath.getId(), signCon);
        ApiService.contractApi().sign(pushedHist.getId(), signCon);
        ApiService.contractApi().sign(pushedAcc.getId(), signCon);
        ApiService.contractApi().sign(pushedMus.getId(), signCon);

    }
}
