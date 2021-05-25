package strategy;

import controller.contract.ContractConfirmController;
import model.contract.ContractRenewalModel;
import stream.Contract;
import view.ViewUtility;
import view.form.ContractSelection;
import view.form.TutorSelection;

/**
 * A form of RenewStrategy by renewing with old existing terms
 */
public class RenewByOldTerms implements RenewStrategy {

    private ContractRenewalModel contractRenewalModel;

    /**
     * Constructs a RenewByOldTerms
     * @param contractRenewalModel a ContractRenewalModel
     */
    public RenewByOldTerms(ContractRenewalModel contractRenewalModel) {
        this.contractRenewalModel = contractRenewalModel;
    }

    /**
     * Execute renewal process by:
     * 1) Getting the contract to be renewed (whose terms are to be reused)
     * 2) Getting the new tutor selection
     * 3) Confirm the finalized contract
     * @param contractSelection a ContractSelection view to select the contract to be renewed
     */
    @Override
    public void renew(ContractSelection contractSelection) throws NullPointerException {
        int selection = contractSelection.getContractSelection();

        TutorSelection tutorSelection = new TutorSelection(contractRenewalModel.getTutorsList(selection));
        tutorSelection.getSelectTutorButton().addActionListener(e1 -> {
            String tutorUsername = tutorSelection.getTutorOption();
            tutorSelection.dispose();
            contractSelection.dispose();
            System.out.println("From ContractRenewalController: Tutor selected: " + tutorUsername);

            Contract contract = contractRenewalModel.getContractWithOldTerms(selection, tutorUsername);

            new ContractConfirmController(contract, ViewUtility.STUDENT_CODE, false);
        });
    }
}
