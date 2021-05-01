package model.dashboard;

import entity.DashboardStatus;
import lombok.Getter;
import observer.OSubject;
import service.ApiService;
import service.ExpiryService;
import stream.Bid;
import stream.Contract;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class DashboardModel extends OSubject {

    private String userId;
    private ApiService apiService;
    private List<Contract> contractsList;
    protected String errorText;

    public DashboardModel(String userId) {
        this.userId = userId;
        this.apiService = new ApiService();
        this.contractsList = new ArrayList<>();
        this.errorText = "";
        refresh();
    }

    public void refresh() {
        // do checks for all bids to see if any of them are expired
//        ExpiryService checkExpired = new ExpiryService();
//        User user = userApi.getUser(userId);
//        boolean expired = user.getInitiatedBids().stream()
//                                .anyMatch(checkExpired::checkIsExpired);
        // Update contractList
        contractsList = apiService.getContractApi().getAll().stream()
                .filter(c -> c.getFirstParty().getId().equals(userId)
                        || c.getSecondParty().getId().equals(userId))
                .collect(Collectors.toList());
        notifyObservers();
    }

    public DashboardStatus getStatus() {
        Bid currentBid = apiService.getUserApi().get(userId).getInitiatedBids().stream()
                                .filter(b -> b.getDateClosedDown() == null)
                                .findFirst()
                                .orElse(null);
        System.out.println(currentBid);
        if (currentBid != null) {
            ExpiryService expiryService = new ExpiryService();
            if (!expiryService.checkIsExpired(currentBid)) {
                errorText = "You already have a bid in progress, displaying active bid";
                notifyObservers();
                return currentBid.getType().equalsIgnoreCase("Open")? DashboardStatus.OPEN: DashboardStatus.CLOSE;
            }
            else {
                return DashboardStatus.PASS;
            }
        } else if (contractsList.size() == 5) {
            errorText = "Error, you already have 5 Contracts";
            notifyObservers();
            return DashboardStatus.MAX;
        }
        return DashboardStatus.PASS;
    }

}
