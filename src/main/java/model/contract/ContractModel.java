package model.contract;

import lombok.Getter;
import service.Service;
import stream.Contract;

import java.util.Date;

@Getter
public class ContractModel {
    
    private String contractId;
    private Contract contract;
    protected String errorText; // TODO: REMOVE THIS

    public ContractModel(Contract contract) {
        this.contract = contract;
        this.errorText = "";
        createContract(contract);
    }

    public void createContract(Contract contract) {
        Contract contractCreated = Service.contractApi.add(contract);
        contractId = contractCreated.getId(); // update contract id after creation
    }

    public void acceptContract() {
        // only student need to sign contract, tutor no need
        Service.contractApi.sign(contractId, new Contract(new Date()));
    }

    public void declineContract() {
        Service.contractApi.remove(contractId);
    }

    // use this to display in the ContractView
    public Contract getContract() {
        return Service.contractApi.get(contractId);
    }

    public void setErrorText(String errorText){
        this.errorText = errorText;
    }
}
