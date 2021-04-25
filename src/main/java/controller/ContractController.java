package controller;

import model.ContractModel;
import stream.Contract;

public class ContractController {

    private ContractModel contractModel;

    public ContractController(ContractModel contractModel) {
        this.contractModel = contractModel;
    }

    public void createContract(Contract contract) {
        contractModel.createContract(contract);
    }

    public void listenAccept() {
        contractModel.acceptContract();
        // delete view after accepting
    }

//    public void listenReject() {
//        contractModel.declineContract();
//        // delete view after rejecting
//    }


}
