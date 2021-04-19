package controller;

import model.ContractModel;
import stream.Contract;
import view.ContractView;

public class ContractController {

    private ContractModel contractModel;
    private ContractView contractView;

    public ContractController(ContractModel contractModel, ContractView contractView) {
        this.contractModel = contractModel;
        this.contractView = contractView;
    }

    public void createContract(Contract contract) {
        contractModel.createContract(contract);
    }

    public void listenAccept() {
        contractModel.acceptContract();
        // delete view after accepting
    }

    public void listenReject() {
        contractModel.declineContract();
        // delete view after rejecting
    }


}
