package strategy;

import controller.contract.ContractConfirmController;
import entity.BidInfo;
import model.contract.ContractRenewalModel;
import stream.Contract;
import view.ViewUtility;
import view.form.TermsCreation;
import view.form.TermsSelection;
import view.form.TutorSelection;

import java.awt.event.ActionEvent;

public class RenewByNewTerms implements RenewStrategy {

    private ContractRenewalModel contractRenewalModel;

    public RenewByNewTerms(ContractRenewalModel contractRenewalModel) {
        this.contractRenewalModel = contractRenewalModel;
    }

    @Override
    public void renew(TermsSelection termsSelection) throws NullPointerException {
        int selection = termsSelection.getContractSelection();

        TermsCreation termsCreation = new TermsCreation();
        termsCreation.getSubmitNewTermsButton().addActionListener(e1 ->
                renewNewTerms(e1, termsSelection, termsCreation, selection));
    }

    private void renewNewTerms(ActionEvent e, TermsSelection termsSelection,
                               TermsCreation termsCreation, int selection) {
        try {
            BidInfo newTerms = extractNewTerms(termsCreation);
            termsCreation.dispose();
            System.out.println("From ContractRenewalController: Extracted: " + newTerms);

            TutorSelection tutorSelection = new TutorSelection(contractRenewalModel.getTutorsList(selection));
            tutorSelection.getSelectTutorButton().addActionListener(e1 -> {
                String tutorUsername = tutorSelection.getTutorOption();
                tutorSelection.dispose();
                termsSelection.dispose();
                System.out.println("From ContractRenewalController: Tutor selected: " + tutorUsername);

                Contract contract = contractRenewalModel.getContractWithNewTerms(selection, newTerms, tutorUsername);

                new ContractConfirmController(contract, ViewUtility.STUDENT_CODE, false);
            });

        } catch (NullPointerException ex) {
            termsCreation.getErrorLabel().setText("Incomplete form!");
        }
    }

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
