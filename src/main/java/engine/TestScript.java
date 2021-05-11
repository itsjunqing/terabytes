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
     * Example of usage: TestScript.clearData()
     * This will clear ALL Message -> ALL Bid -> ALL Contract
     */
    private static void clearData() {
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
     * Example of usage: TestScript.clearData("123456")
     * This will clear ALL Message -> ALL Bid -> ALL Contract of a particular user
     */
    private static void clearData(String userId) {
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
}
