package strategy;

import controller.contract.ContractConfirmController;
import entity.BidInfo;
import model.contract.ContractRenewalModel;
import stream.Contract;
import view.ViewUtility;
import view.contract.ContractSelection;
import view.form.TermsCreation;
import view.form.TutorSelection;

import java.awt.event.ActionEvent;

/**
 * A form of RenewStrategy by renewing with new terms
 */
public class RenewByNewTerms implements RenewStrategy {

    private ContractRenewalModel contractRenewalModel;

    /**
     * Constructs a RenewByNewTerms
     * @param contractRenewalModel a ContractRenewalModel
     */
    public RenewByNewTerms(ContractRenewalModel contractRenewalModel) {
        this.contractRenewalModel = contractRenewalModel;
    }

    /**
     * Execute renewal process by:
     * 1) Getting the contract to be renewed (whose terms are to be reused)
     * 2) Getting the new terms initiated
     * 3) Getting the new tutor selection
     * 4) Confirm the finalized contract
     * @param contractSelection a ContractSelection view to select the contract to be renewed
     */
    @Override
    public void renew(ContractSelection contractSelection) throws NullPointerException {
        int selection = contractSelection.getContractSelection();

        TermsCreation termsCreation = new TermsCreation();
        termsCreation.getSubmitNewTermsButton().addActionListener(e1 ->
                renewNewTerms(e1, contractSelection, termsCreation, selection));
    }

    /**
     * Executes new terms renewal
     */
    private void renewNewTerms(ActionEvent e, ContractSelection contractSelection,
                               TermsCreation termsCreation, int selection) {
        try {
            BidInfo newTerms = extractNewTerms(termsCreation);
            termsCreation.dispose();
            System.out.println("From ContractRenewalController: Extracted: " + newTerms);

            TutorSelection tutorSelection = new TutorSelection(contractRenewalModel.getTutorsList(selection));
            tutorSelection.getSelectTutorButton().addActionListener(e1 -> {
                String tutorUsername = tutorSelection.getTutorOption();
                tutorSelection.dispose();
                contractSelection.dispose();
                System.out.println("From ContractRenewalController: Tutor selected: " + tutorUsername);

                Contract contract = contractRenewalModel.getContractWithNewTerms(selection, newTerms, tutorUsername);

                new ContractConfirmController(contract, ViewUtility.STUDENT_CODE, false);
            });

        } catch (NullPointerException ex) {
            termsCreation.getErrorLabel().setText("Incomplete form!");
        }
    }

    /**
     * Extract the new terms initiated from the TermCreation form
     */
    private BidInfo extractNewTerms(TermsCreation form) throws NullPointerException {
        int sessions = form.getNumOfSessions();
        String day = form.getDay();
        String time = form.getTime();
        int duration = form.getDuration();
        int rate = form.getRate();
        String initiatorId = contractRenewalModel.getUserId();
        BidInfo terms = new BidInfo(initiatorId, day, time, duration, rate, sessions);
        terms.setFreeLesson(false); // default to false
        return terms;
    }

}
