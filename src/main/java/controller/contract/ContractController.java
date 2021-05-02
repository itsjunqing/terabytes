package controller.contract;

import controller.EventListener;
import model.contract.ContractModel;
import stream.Contract;
import view.contract.ContractView;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ContractController implements EventListener {

    private ContractModel contractModel;
    private ContractView contractView; // replace this with ContractView

    public ContractController(Contract contract) {
        this.contractModel = new ContractModel(contract);
//        SwingUtilities.invokeLater(() -> {
            this.contractView = new ContractView(contractModel);
            listenViewActions();
//        });
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
