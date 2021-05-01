package model.dashboard;

import entity.DashboardStatus;
import lombok.Getter;
import model.BasicModel;
import service.ApiService;
import stream.Bid;
import stream.Contract;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class DashboardModel extends BasicModel {

    private List<Contract> contractsList;
    protected String errorText;

    public DashboardModel(String userId) {
        this.userId = userId;
        this.contractsList = new ArrayList<>();
        this.errorText = "";
        refresh();
    }

    public void refresh() {
        contractsList = ApiService.contractApi.getAll().stream()
                .filter(c -> c.getFirstParty().getId().equals(userId)
                        || c.getSecondParty().getId().equals(userId))
                .collect(Collectors.toList());
        oSubject.notifyObservers();
    }

    public DashboardStatus getStatus() {
        Bid currentBid = ApiService.userApi.get(userId).getInitiatedBids().stream()
                                .filter(b -> b.getDateClosedDown() == null)
                                .findFirst()
                                .orElse(null);
        System.out.println(currentBid);
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

}
