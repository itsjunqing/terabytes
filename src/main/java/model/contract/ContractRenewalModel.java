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

/**
 * A Class of ContractRenewalModel to store the information on renewing contracts by Student
 */
@Getter
public class ContractRenewalModel extends BasicModel {

    private List<Contract> expiredContracts;
    private List<Contract> activeContracts;
    private List<Contract> allContracts;

    /**
     * Constructs a ContractRenewalModel
     * @param userId a String of user id
     */
    public ContractRenewalModel(String userId) {
        this.userId = userId;
        expiredContracts = new ArrayList<>();
        activeContracts = new ArrayList<>();
        allContracts = new ArrayList<>();
        refresh();
    }

    /**
     * Refreshes the model
     */
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
     * Returns a list of tutors that have the corresponding subject and competency requirement
     * of the existing contract to be renewed
     * @param selection a Contract to be renewed
     * @return a list of String of tutor username
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

    /**
     * Returns a new Contract with renewed with new terms
     * @param selection contract to be renewed
     * @param newTerms new terms established
     * @param tutorUsername the tutor whose contract to be established with
     * @return a Contract object
     */
    public Contract getContractWithNewTerms(int selection, BidInfo newTerms, String tutorUsername) {
        Contract oldContract = allContracts.get(selection-1);
        User tutor = Utility.getUser(tutorUsername);
        return BuilderService.buildContract(oldContract, newTerms, tutor.getId());
    }

    /**
     * Returns a new Contract with renewed with old terms
     * @param selection contract to be renewed
     * @param tutorUsername the tutor whose contract to be established with
     * @return a Contract object
     */
    public Contract getContractWithOldTerms(int selection, String tutorUsername) {
        Contract oldContract = allContracts.get(selection-1);
        User tutor = Utility.getUser(tutorUsername);
        return BuilderService.buildContract(oldContract, tutor.getId());
    }


}