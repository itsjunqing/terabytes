package strategy;

import controller.contract.ContractConfirmController;
import model.contract.ContractRenewalModel;
import stream.Contract;
import view.ViewUtility;
import view.form.TermsSelection;
import view.form.TutorSelection;

public class RenewByOldTerms implements RenewStrategy {

    private ContractRenewalModel contractRenewalModel;

    public RenewByOldTerms(ContractRenewalModel contractRenewalModel) {
        this.contractRenewalModel = contractRenewalModel;
    }

    @Override
    public void renew(TermsSelection termsSelection) {
        int selection = termsSelection.getContractSelection();

        TutorSelection tutorSelection = new TutorSelection(contractRenewalModel.getTutorsList(selection));
        tutorSelection.getSelectTutorButton().addActionListener(e1 -> {
            String tutorUsername = tutorSelection.getTutorOption();
            tutorSelection.dispose();
            termsSelection.dispose();
            System.out.println("From ContractRenewalController: Tutor selected: " + tutorUsername);

            Contract contract = contractRenewalModel.getContractWithOldTerms(selection, tutorUsername);

            new ContractConfirmController(contract, ViewUtility.STUDENT_CODE, false);
        });
    }
}
