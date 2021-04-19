package model;

import api.ContractApi;
import stream.Contract;

import java.util.Date;

public class ContractModel {

    private ContractApi contractApi;
    private String contractId;

    public ContractModel() {
        this.contractApi = new ContractApi();
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
}
