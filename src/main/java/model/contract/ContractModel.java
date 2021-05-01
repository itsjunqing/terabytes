package model.contract;

import lombok.Getter;
import service.ApiService;
import stream.Contract;

import java.util.Date;

@Getter
public class ContractModel {
    private ApiService apiService;
    private String contractId;
    private Contract contract;
    protected String errorText;


    public ContractModel(Contract contract) {
        this.apiService = new ApiService();
        this.contract = contract;
        this.errorText = "";
        createContract(contract);
    }

    public void createContract(Contract contract) {
        Contract contractCreated = apiService.getContractApi().add(contract);
        contractId = contractCreated.getId(); // update contract id after creation
    }

    public void acceptContract() {
        // only student need to sign contract, tutor no need
        apiService.getContractApi().sign(contractId, new Contract(new Date()));
    }

    public void declineContract() {
        apiService.getContractApi().remove(contractId);
    }

    // use this to display in the ContractView
    public Contract getContract() {
        return apiService.getContractApi().get(contractId);
    }

    public void setErrorText(String errorText){
        this.errorText = errorText;
    }
}
