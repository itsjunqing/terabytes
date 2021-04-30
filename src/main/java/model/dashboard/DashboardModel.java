package model.dashboard;

import api.BidApi;
import api.ContractApi;
import entity.DashboardStatus;
import lombok.Getter;
import observer.OSubject;
import stream.Bid;
import stream.Contract;
import stream.User;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class DashboardModel extends OSubject {

    private User user;
    private ContractApi contractApi;
    private BidApi bidApi;
    private List<Contract> contractsList;

    public DashboardModel(User user) {
        this.user = user;
        this.contractApi = new ContractApi();
        this.bidApi = new BidApi();
        refresh(); // Note: MUST populate initial values otherwise view is not created
    }

    public void refresh() {
        contractsList = contractApi.getAllContracts().stream()
                .filter(c -> c.getFirstParty().getId().equals(user.getId())
                        || c.getSecondParty().getId().equals(user.getId()))
                .collect(Collectors.toList());
//        notifyObservers();
    }

    public DashboardStatus getStatus() {
        Bid currentBid = bidApi.getAllBids().stream()
                .filter(b -> b.getDateClosedDown() == null)
                .filter(b -> b.getInitiator().getId().equals(user.getId()))
                .findFirst()
                .orElse(null);
        refresh();
        System.out.println(currentBid);
        if (currentBid != null) {
            return currentBid.getType().equalsIgnoreCase("Open")? DashboardStatus.OPEN: DashboardStatus.CLOSE;
        } else if (contractsList.size() == 5) {
            return DashboardStatus.MAX;
        }
        return DashboardStatus.PASS;
    }

    public String getUserId() {
        return user.getId();
    }
}
