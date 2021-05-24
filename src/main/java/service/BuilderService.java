package service;

import builder.BidBuilder;
import builder.ContractBuilder;

/**
 * A builder class that builds objects to be re-used.
 */
public class BuilderService {

    private static BidBuilder bidBuilder;
    private static ContractBuilder contractBuilder;

    public static BidBuilder bidBuilder() {
        if (bidBuilder == null) {
            bidBuilder = new BidBuilder();
        }
        return bidBuilder;
    }

    public static ContractBuilder contractBuilder() {
        if (contractBuilder == null) {
            contractBuilder = new ContractBuilder();
        }
        return contractBuilder;
    }
}


//    /**
//     * Builds contract with new terms
//     */
//    public static Contract buildContract(Contract contract, BidInfo newTerms) {
//        Contract newContract = new Contract(contract); // copies a new Contract
//        System.out.println("new contract copied = " + newContract);
//        // calculate expiry date based on date creation
//        Calendar c = Calendar.getInstance();
//        c.setTime(newContract.getDateCreated());
//        c.add(Calendar.MONTH, Constants.DEFAULT_CONTRACT_DURATION);
//        Date expiryDate = c.getTime();
//
//        // calculate payment = rate per session * number of session
//        Payment newPayment = new Payment(newTerms.getRate() * newTerms.getNumberOfSessions());
//
//        // get the updated lesson terms
//        Lesson newLesson = new Lesson(Utility.getSubjectName(newContract.getSubjectId()), newTerms.getDay(),
//                newTerms.getTime(), newTerms.getDuration(), newTerms.getNumberOfSessions(), newTerms.isFreeLesson());
//
//        // update the terms of the expired (old contract) to new terms
//        newContract.setExpiryDate(expiryDate);
//        newContract.setPaymentInfo(newPayment);
//        newContract.setLessonInfo(newLesson);
//        System.out.println("From BuilderService: Contract built: " + newContract);
//        return newContract;
//    }

//    /**
//     * Builds contract with existing terms with a new tutor
//     */
//    public static Contract buildContract(Contract contract, String tutorId) {
//        Contract newContract = new Contract(contract); // copies a new Contract
//        newContract.setSecondPartyId(tutorId); // set to new tutor
//
//        // calculate expiry date based on date creation
//        Calendar c = Calendar.getInstance();
//        c.setTime(newContract.getDateCreated());
//        c.add(Calendar.MONTH, Constants.DEFAULT_CONTRACT_DURATION);
//        Date expiryDate = c.getTime();
//
//        // update expiry date to default months
//        newContract.setExpiryDate(expiryDate);
//
//        return newContract;
//    }
