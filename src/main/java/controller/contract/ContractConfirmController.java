package controller.contract;

import controller.EventListener;
import model.contract.ContractConfirmModel;
import stream.Contract;
import view.contract.ContractConfirmView;

import java.awt.event.ActionEvent;

/**
 * A controller that deals with confirming a contract details
 * along with getting contract duration input from user (student/tutor)
 *
 * Note: the contract is only pushed to the API when it is confirmed,
 * hence the ContractConfirmModel only stores the Contract object that is yet to be posted
 * (and not the returned Contract object after posted)
 */
public class ContractConfirmController implements EventListener {

    private ContractConfirmModel contractConfirmModel;
    private ContractConfirmView contractConfirmView;

    public ContractConfirmController(Contract contract, int type, boolean sign) {
        this.contractConfirmModel = new ContractConfirmModel(contract, type, sign);
        this.contractConfirmView = new ContractConfirmView(contractConfirmModel);
        listenViewActions();
    }

    @Override
    public void listenViewActions() {
        contractConfirmView.getConfirmSignButton().addActionListener(this::handleSign);
    }

    /**
     * A helper function that signs the contract
     * @param e
     */
    private void handleSign(ActionEvent e) {
        System.out.println("From ContractConfirmController: Confirm/Sign Button is pressed");

        // get the contract duration -> update expiry date -> sign
        int months = contractConfirmView.getContractDuration();
        contractConfirmModel.updateExpiry(months);

        // if this contract is to be confirmed -> sign, then sign
        // only upon contract renewal, it will not be signed by student (but by tutor)
        contractConfirmModel.signContract(contractConfirmModel.isSign());

        // dispose view
        contractConfirmView.dispose();
    }
}
