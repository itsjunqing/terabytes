package model.contract;

import entity.BidInfo;
import entity.Preference;
import entity.Utility;
import lombok.Getter;
import model.BasicModel;
import service.ApiService;
import service.BuilderService;
import stream.Contract;
import stream.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ContractRenewalModel extends BasicModel {

    private List<Contract> expiredContracts;
    private List<Contract> activeContracts;
    private List<Contract> allContracts;

    public ContractRenewalModel(String userId) {
        this.userId = userId;
        expiredContracts = new ArrayList<>();
        activeContracts = new ArrayList<>();
        allContracts = new ArrayList<>();
        refresh();
    }

    @Override
    public void refresh() {
        errorText = "";
        expiredContracts.clear();
        activeContracts.clear();
        allContracts.clear();
        List<Contract> contracts = ApiService.contractApi().getAll().stream()
                .filter(c -> c.getFirstParty().getId().equals(userId))
                .collect(Collectors.toList());

        Date today = new Date();
        for (Contract c: contracts) {
            Date expiry = c.getExpiryDate();
            if (expiry.before(today)) {
                expiredContracts.add(c);
            } else {
                activeContracts.add(c);
            }
            allContracts.add(c);
        }

        System.out.println("Expired contracts are: ");
        expiredContracts.stream().forEach(c -> System.out.println(c));

        System.out.println("Active contracts are: ");
        activeContracts.stream().forEach(c -> System.out.println(c));

        oSubject.notifyObservers();
    }

    /**
     * Returns the list of tutors that have the subject and competency requirement of the existing contract.
     */
    public List<String> getTutorsList(int selection) {
        Contract existingContract = allContracts.get(selection-1);

        Preference preference = existingContract.getPreference();
        String qualification = preference.getQualification().toString();
        int competency = preference.getCompetency();
        String subject = existingContract.getSubject().getName();

        return ApiService.userApi().getAll().stream()
                .filter(User::getIsTutor)
                .filter(u -> {
                    boolean hasQualification = u.getQualifications().stream()
                            .anyMatch(q -> q.getTitle().equals(qualification));

                    boolean hasCompetency = u.getCompetencies().stream()
                            .anyMatch(c -> c.getLevel() - 2 >= competency
                                    && c.getSubject().getName().equals(subject));
                    return (hasQualification && hasCompetency);
                })
                .map(User::getUserName)
                .collect(Collectors.toList());
    }

    public Contract getContractWithNewTerms(int selection, BidInfo newTerms, String tutorUsername) {
        Contract oldContract = allContracts.get(selection-1);
        User tutor = Utility.getUser(tutorUsername);
        return BuilderService.buildContract(oldContract, newTerms, tutor.getId());
    }

    public Contract getContractWithOldTerms(int selection, String tutorUsername) {
        Contract oldContract = allContracts.get(selection-1);
        User tutor = Utility.getUser(tutorUsername);
        return BuilderService.buildContract(oldContract, tutor.getId());
    }

    public int getActiveSize() {
        return activeContracts.size();
    }
    public int getExpiredSize() {
        return expiredContracts.size();
    }

}


//    public Contract renewNewTerms(int selection, BidInfo newTerms) {
//        Contract oldContract = expiredContracts.get(selection-1);
//        return BuilderService.buildContract(oldContract, newTerms);
//    }
//
//    public Contract renewExistingTerms(int selection, String newTutor) {
//        Contract oldContract = expiredContracts.get(selection-1);
//        User tutor = Utility.getUser(newTutor);
//        return BuilderService.buildContract(oldContract, tutor.getId());
//    }