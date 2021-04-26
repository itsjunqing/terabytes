package model;

import api.ContractApi;
import api.SubjectApi;
import lombok.Getter;
import observer.OSubject;
import stream.Contract;
import stream.User;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class DashboardModel extends OSubject {
    private User user; // DashboardView can access user to determine if it's tutor / user to initDisplay the correct view
    private ContractApi contractApi;
    private SubjectApi subjectApi;
    private List<Contract> contractsList;

    public DashboardModel(User user) {
        this.user = user;
        this.contractApi = new ContractApi();
        refresh(); // populate initial values
    }

    public void refresh() {
        contractsList = contractApi.getAllContracts().stream()
                .filter(c -> c.getFirstParty().getId().equals(user.getId()))
                .collect(Collectors.toList());
//        notifyObservers();
    }

    public String getUserId() {
        return user.getId();
    }

    public List<Contract> getContractsList() {
        return contractsList;
    }
}
