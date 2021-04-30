package controller.contract;

import model.contract.ContractModel;
import stream.Contract;
import view.contract.ContractView;

import java.awt.event.ActionEvent;

public class ContractController {

    private ContractModel contractModel;
    private ContractView contractView; // replace this with ContractView
    private String contractId;


    public ContractController(Contract contract) {
        this.contractId = contractId;
        this.contractModel = new ContractModel(contract);
        this.contractView = new ContractView(contractModel);
        listenViewActions();
    }

    public void listenViewActions() {
        contractView.getConfirmButton().addActionListener(this::handleAccept);
        contractView.getCancelButton().addActionListener(this::handleDecline);
    }

    public void createContract(Contract contract) {
        contractModel.createContract(contract);
    }

    public void handleAccept(ActionEvent e) {
        contractModel.acceptContract();
        // delete view after accepting
        contractView.dispose();
    }

    public void handleDecline(ActionEvent e) {
        contractModel.declineContract();
        // delete view after rejecting
        contractView.dispose();
    }


}
