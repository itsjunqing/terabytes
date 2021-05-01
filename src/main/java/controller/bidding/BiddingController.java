package controller.bidding;

import controller.contract.ContractController;
import stream.Contract;

/**
 * Remaining parts:
 * - construction of ContractController, View, Model
 */
public abstract class BiddingController {

    protected void handleContract(Contract contract) {
        ContractController contractController = new ContractController(contract);
    }

    public abstract void listenViewActions();


}
