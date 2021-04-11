package model;
import api.ContractApi;
import stream.Contract;
import stream.User;
import java.util.List;
import java.util.stream.Collectors;

public class DashboardModel {
    private User user;
    private ContractApi contractApi;


    // Get Contracts
    public List<Contract> getContract(String id) {
        List<Contract> contracts = contractApi.getAllContracts();
        return contracts.stream()
                .filter(c -> c.getFirstPartyId().equals(id)|| c.getFirstPartyId().equals(id))
                .collect(Collectors.toList());
    }

}
