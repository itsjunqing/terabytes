package model.contract;

import api.ContractApi;
import lombok.Getter;
import stream.Contract;

import java.util.Date;

@Getter
public class ContractModel {

    private ContractApi contractApi;
    private String contractId;
    private Contract contract;
    protected String errorText;


    public ContractModel(Contract contract) {
        this.contractApi = new ContractApi();
        this.contract = contract;
        this.errorText = "";
        createContract(contract);
//        this.contractId = contract.getId();
    }

    public void obtainContract (){
        for (Contract c :contractApi.getAllContracts()){
            if (c.getId() == contractId){
                this.contract = c;
            }
        };
    }

    public void createContract(Contract contract) {
        Contract contractCreated = contractApi.addContract(contract);
        contractId = contractCreated.getId(); // update contract id after creation
    }

    public void acceptContract() {
        // only student need to sign contract, tutor no need
        contractApi.signContract(contractId, new Contract(new Date()));
    }

    public void declineContract() {
        contractApi.removeContract(contractId);
    }

    // use this to display in the ContractView
    public Contract getContract() {
        return contractApi.getContract(contractId);
    }

    public void setErrorText(String errorText){
        this.errorText = errorText;
    }
}
