package controller.bidding;

import controller.EventListener;
import controller.contract.ContractConfirmController;
import stream.Contract;
import view.ViewUtility;


public abstract class BiddingController implements EventListener {

    protected void handleContract(Contract contract) {
        System.out.println("From BiddingController: Contract is being confirmed now");
        new ContractConfirmController(contract, ViewUtility.STUDENT_CODE, true);
    }

    public abstract void listenViewActions();

}
