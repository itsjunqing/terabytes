package model.dashboard;

import api.BidApi;
import api.ContractApi;
import api.UserApi;
import entity.DashboardStatus;
import lombok.Getter;
import model.CheckExpired;
import observer.OSubject;
import stream.Bid;
import stream.Contract;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class DashboardModel extends OSubject {

//    private User user;
    private String userId;
    private UserApi userApi;
    private ContractApi contractApi;
    private BidApi bidApi;
    private List<Contract> contractsList;
    protected String errorText;


    public DashboardModel(String userId) {
        this.userId = userId;
        this.userApi = new UserApi();
        this.contractApi = new ContractApi();
        this.bidApi = new BidApi();
        this.errorText = "";
        refresh();
    }

    public void refresh() {
        // do checks for all bids to see if any of them are expired
//        CheckExpired checkExpired = new CheckExpired();
//        User user = userApi.getUser(userId);
//        boolean expired = user.getInitiatedBids().stream()
//                                .anyMatch(checkExpired::checkIsExpired);
        // Update contractList
        contractsList = contractApi.getAllContracts().stream()
                .filter(c -> c.getFirstParty().getId().equals(userId)
                        || c.getSecondParty().getId().equals(userId))
                .collect(Collectors.toList());
        notifyObservers();
    }

    public DashboardStatus getStatus() {
        Bid currentBid = userApi.getUser(userId).getInitiatedBids().stream()
                                .filter(b -> b.getDateClosedDown() == null)
                                .findFirst()
                                .orElse(null);
        System.out.println(currentBid);
        if (currentBid != null) {
            CheckExpired checkExpired = new CheckExpired();
            if (!checkExpired.checkIsExpired(currentBid)) {
                return currentBid.getType().equalsIgnoreCase("Open")? DashboardStatus.OPEN: DashboardStatus.CLOSE;
            }
            else {
                return DashboardStatus.PASS;
            }
        } else if (contractsList.size() == 5) {
            return DashboardStatus.MAX;
        }
        return DashboardStatus.PASS;
    }

    public void setErrorText(String errorText){
        this.errorText = errorText;
    }
}
