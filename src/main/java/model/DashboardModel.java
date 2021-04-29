package model;

import api.BidApi;
import api.ContractApi;
import api.SubjectApi;
import com.google.gson.internal.bind.util.ISO8601Utils;
import lombok.Getter;
import observer.OSubject;
import stream.Bid;
import stream.Contract;
import stream.User;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class DashboardModel extends OSubject {
    private User user; // DashboardView can access user to determine if it's tutor / user to initDisplay the correct view
    private ContractApi contractApi;
    private BidApi bidApi;
    private SubjectApi subjectApi;
    private List<Contract> contractsList;

    public DashboardModel(User user) {
        this.user = user;
        this.contractApi = new ContractApi();
        this.bidApi = new BidApi();
        refresh(); // Note: MUST populate initial values otherwise view is not created
    }

    public void refresh() {
        contractsList = contractApi.getAllContracts().stream()
                .filter(c -> c.getFirstParty().getId().equals(user.getId()) || c.getSecondParty().getId().equals(user.getId()))
                .collect(Collectors.toList());
//        notifyObservers();
    }
    // TODO: maybe change return type to boolean?
    public String checkBids(){
        List<Bid> bidList = bidApi.getAllBids().stream()
                .filter(b -> b.getDateClosedDown() == null)
                .filter(b -> b.getInitiator().getId().equals(user.getId()))
                .collect(Collectors.toList());
        System.out.println(bidList.size());
        if (bidList.size() != 0){
            return bidList.get(0).getType();
        } else if (contractApi.getAllContracts().stream().filter(c -> c.getFirstPartyId().equals(user.getId())).count() == 5) {
            return "max";
        } else { return "pass";

        }
    }

    public String getUserId() {
        return user.getId();
    }

    public List<Contract> getContractsList() {
        return contractsList;
    }
}
