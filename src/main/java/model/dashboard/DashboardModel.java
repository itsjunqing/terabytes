package model.dashboard;

import entity.DashboardStatus;
import entity.Utility;
import lombok.Getter;
import model.BasicModel;
import service.ApiService;
import stream.Bid;
import stream.Contract;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A class of DashboardModel to store the data and content in the dashboard for tutor/student.
 */
@Getter
public class DashboardModel extends BasicModel {

    private List<Contract> contractsList;

    /**
     * Constructs a DashboardModel
     * @param userId a String user id
     */
    public DashboardModel(String userId) {
        this.userId = userId;
        this.contractsList = new ArrayList<>();

        this.errorText = "";
        refresh();
    }

    /**
     * Refreshes the model.
     */
    @Override
    public void refresh() {
        this.errorText = "";
        contractsList = ApiService.contractApi().getAll().stream()
                .filter(c -> c.getFirstParty().getId().equals(userId)
                        || c.getSecondParty().getId().equals(userId))
                .filter(c -> c.getDateSigned() != null) // must be signed contracts
                .collect(Collectors.toList());
        oSubject.notifyObservers();
    }

    /**
     * Returns the status of the dashboard. Check DashboardStatus class for more information.
     * @return a DashboardStatus
     */
    public DashboardStatus getDashboardStatus() {
        Bid currentBid = ApiService.bidApi().getAll().stream()
                .filter(b -> b.getInitiator().getId().equals(userId))
                .filter(b -> b.getDateClosedDown() == null)
                .findFirst()
                .orElse(null);
        if (currentBid != null) {
            if (!expiryService.checkIsExpired(currentBid)) {
                errorText = "You already have a bid in progress, displaying active bid";
                oSubject.notifyObservers();
                return currentBid.getType().equalsIgnoreCase("Open")? DashboardStatus.OPEN: DashboardStatus.CLOSE;
            }
            else {
                return DashboardStatus.PASS;
            }
        } else if (getActiveContracts().size() == 5) {
            errorText = "Error, you already have 5 Active Contracts";
            oSubject.notifyObservers();
            return DashboardStatus.MAX;
        }
        return DashboardStatus.PASS;
    }


    /**
     * Get the list of contracts to be renewed for Tutor
     * These contracts are contracts whose renewal are initiated by Students but require the
     * need of Tutor's approval/agreement.
     * @return a list of Contract objects
     */
    public List<Contract> getRenewingContracts() {
        return ApiService.contractApi().getAll().stream()
                .filter(c -> c.getSecondParty().getId().equals(userId)) // for tutor matching second party
                .filter(c -> c.getDateSigned() == null) // for contract unsigned
                .collect(Collectors.toList());
    }

    /**
     * Signs or remove a contract to be renewed
     * @param contract a contract to be responded
     * @param toSign true for signing / confirming and false otherwise
     */
    public void executeRenewalResponse(Contract contract, boolean toSign) {
        String contractId = contract.getId();
        if (toSign) {
            ApiService.contractApi().sign(contractId, new Contract(new Date()));
        } else {
            ApiService.contractApi().remove(contractId);
        }
    }

    /**
     * Gets the name of the current dashboard holder.
     * @return a String of name
     */
    public String getName() {
        return Utility.getFullName(userId);
    }

    /**
     * Returns the top 5 latest contracts
     * @return List of top 5 latest contracts
     */
    public List<Contract> getTopFiveContracts() {
        return contractsList.stream()
                // custom comparator to sort contract based on date
                .sorted(new Comparator<Contract>() {
                    public int compare(Contract contractA, Contract contractB) {
                        return contractA.getDateCreated().compareTo(contractB.getDateCreated());
                    // reverse list to have latest dates first
                    }}.reversed())
                // only collect the first 5 items
                .limit(5)
                .collect(Collectors.toList());
    }

    private List<Contract> getActiveContracts() {
        Date today = new Date();
        return contractsList.stream()
                .filter(c -> {
                    Date expiry = c.getExpiryDate();
                    return !expiry.before(today);
                })
                .collect(Collectors.toList());
    }
}
