package model.contract;

import entity.Preference;
import model.BasicModel;
import service.ApiService;
import stream.Contract;
import stream.User;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ContractRenewalModel extends BasicModel {

    private List<Contract> expiredContracts;

    public ContractRenewalModel(String userId) {
        this.userId = userId;
        refresh();
    }


    @Override
    public void refresh() {
        this.errorText = "";
        Date today = new Date();
        expiredContracts = ApiService.contractApi().getAll().stream()
                .filter(c -> c.getExpiryDate().before(today))
                .collect(Collectors.toList());
//        oSubject.notifyObservers();
    }

    public List<Contract> getAllContracts() {
        return ApiService.contractApi().getAll();
    }

    /**
     * Returns the list of tutors that have the subject and competency requirement of the existing contract.
     * @param existingContract selected contract to be reused
     * @return a list of tutor username
     */
    public List<String> getTutorsList(Contract existingContract) {
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
}
