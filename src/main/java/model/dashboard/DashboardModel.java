package model.dashboard;

import entity.DashboardStatus;
import entity.Utility;
import lombok.Getter;
import model.BasicModel;
import service.ApiService;
import stream.Bid;
import stream.Contract;

import java.util.ArrayList;
import java.util.List;
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
                .collect(Collectors.toList());
        oSubject.notifyObservers();
    }

    /**
     * Returns the status of the dashboard. Check DashboardStatus class for more information.
     * @return a DashboardStatus
     */
    public DashboardStatus getStatus() {
        // don't use the code below, buggy
//        Bid currentBid = ApiService.userApi().get(userId).getInitiatedBids().stream()
//                                .filter(b -> b.getDateClosedDown() == null)
//                                .findFirst()
//                                .orElse(null);
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
        } else if (contractsList.size() == 5) {
            errorText = "Error, you already have 5 Contracts";
            oSubject.notifyObservers();
            return DashboardStatus.MAX;
        }
        return DashboardStatus.PASS;
    }

    /**
     * Gets the name of the current dashboard holder.
     * @return a String of name
     */
    public String getName() {
        return Utility.getFullName(userId);
    }


}
