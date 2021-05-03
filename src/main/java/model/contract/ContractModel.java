package model.contract;

import lombok.Getter;
import service.ApiService;
import stream.Contract;

import java.util.Date;

/**
 * A Class of ContractModel that stores the data of a Contract formation.
 */
@Getter
public class ContractModel {
    
    private String contractId;
    private Contract contract;
    protected String errorText;

    /**
     * Constructs a ContractModel
     * @param contract a Contract object
     */
    public ContractModel(Contract contract) {
        this.contract = contract;
        this.errorText = "";
        createContract(contract);
    }

    /**
     * Creates a Contract by pushing to the API.
     * @param contract a Contract object
     */
    public void createContract(Contract contract) {
        Contract contractCreated = ApiService.contractApi().add(contract);
        contractId = contractCreated.getId(); // update contract id after creation
    }

    /**
     * Accepts and signs the Contract.
     */
    public void acceptContract() {
        // only student need to sign contract, tutor no need
        ApiService.contractApi().sign(contractId, new Contract(new Date()));
    }

    /**
     * Rejects and delete the Contract.
     */
    public void declineContract() {
        ApiService.contractApi().remove(contractId);
    }

    /**
     * Returns the a Contract based on the contract id.
     * @return a Contract object
     */
    // use this to display in the ContractView
    public Contract getContract() {
        return ApiService.contractApi().get(contractId);
    }

    public void setErrorText(String errorText){
        this.errorText = errorText;
    }
}
