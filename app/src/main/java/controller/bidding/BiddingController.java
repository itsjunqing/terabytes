package controller.bidding;

import controller.EventListener;
import controller.contract.ContractConfirmController;
import stream.Contract;
import view.ViewUtility;

/**
 * A Class of BiddingController to control the actions on Bidding dashboard
 */
public abstract class BiddingController implements EventListener {

    /**
     * Handles the Contract established by requesting for confirmation from the student
     * @param contract contract to be confirmed
     */
    protected void handleContract(Contract contract) {
        System.out.println("From BiddingController: Contract is being confirmed now");
        new ContractConfirmController(contract, ViewUtility.STUDENT_CODE, true);
    }

    public abstract void listenViewActions();

}
