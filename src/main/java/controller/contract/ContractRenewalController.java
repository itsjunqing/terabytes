package controller.contract;

import controller.EventListener;
import entity.Utility;
import model.contract.ContractRenewalModel;
import service.BuilderService;
import stream.Contract;
import stream.User;

public class ContractRenewalController implements EventListener {

    private ContractRenewalModel contractRenewalModel;

    public ContractRenewalController(String userId) {
        this.contractRenewalModel = new ContractRenewalModel(userId);
        listenViewActions();
    }

    @Override
    public void listenViewActions() {
        handleRefresh();
        handleNewTerms();
        handleExistingTerms();
    }

    private void handleRefresh() {
        contractRenewalModel.refresh();
    }

    private void handleNewTerms() {
        int sessions = -1;
        String day = "";
        String time = "";
        int duration = -1;
        int rate = -1;

    }

    private void handleExistingTerms() {
        // TODO: Create ExistingTermsSelection view
        /**
         * 1. Get the selected expired contract
         * 2. Create ExistingTermsView and pass (all contracts except expiredContract) + expiredContract
         * 3. Listen to Select button
         * 4. Get the Contract selected
         * 5. Get the tutor list of the selected contract to be renewed
         * 6. If list empty -> prompt error
         * 7. If list has tutor -> create ContractTutorSelection view and pass the list
         * 8. Listen to Select button
         * 9. Get the username of the tutor selected
         * 10. Get the User of the selected username
         * 11. Create Contract object
         * 12. Create ContractConfirmation Form
         */

        Contract selectedContract = null;
        User tutor = Utility.getUser("someTutorUsername");
        Contract contract = BuilderService.buildContract(selectedContract, tutor.getId());


    }
}
