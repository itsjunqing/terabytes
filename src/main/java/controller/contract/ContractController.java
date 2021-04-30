package controller.contract;

import model.contract.ContractModel;
import stream.Contract;
import view.contract.ContractFinalization;

public class ContractController {

    private ContractModel contractModel;
    private ContractFinalization contractFinalization;

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
