package controller.contract;

import controller.EventListener;
import entity.BidInfo;
import model.contract.ContractRenewalModel;
import stream.Contract;
import view.ViewUtility;
import view.contract.ContractRenewalView;
import view.form.TermsCreation;
import view.form.TermsSelection;
import view.form.TutorSelection;

import java.awt.event.ActionEvent;

public class ContractRenewalController implements EventListener {

    private ContractRenewalModel contractRenewalModel;
    private ContractRenewalView contractRenewalView;

    public ContractRenewalController(String userId) {
        this.contractRenewalModel = new ContractRenewalModel(userId);
        this.contractRenewalView = new ContractRenewalView(contractRenewalModel);
        this.contractRenewalModel.attach(contractRenewalView);
        listenViewActions();
    }

    @Override
    public void listenViewActions() {
        contractRenewalView.getRefreshButton().addActionListener(this::handleRefresh);
        contractRenewalView.getRenewContractButton().addActionListener(this::handleRenew);
    }

    private void handleRefresh(ActionEvent e) {
        contractRenewalModel.refresh();
    }

    private void handleRenew(ActionEvent e) {
        if (contractRenewalModel.getActiveContracts().size() < 5) {
            TermsSelection termsSelection = new TermsSelection(contractRenewalModel.getAllContracts());
            termsSelection.getRenewNewTermsButton().addActionListener(e1 -> handleNewTerms(e1, termsSelection));
            termsSelection.getRenewOldTermsButton().addActionListener(e1 -> handleOldTerms(e1, termsSelection));

        } else {
            contractRenewalView.getErrorLabel().setText("Error, 5 contracts are already in active!");
        }
    }

    private void handleNewTerms(ActionEvent e, TermsSelection termsSelection) {
        try {
            int selection = termsSelection.getContractSelection();

            TermsCreation termsCreation = new TermsCreation();
            termsCreation.getSubmitNewTermsButton().addActionListener(e1 ->
                    renewNewTerms(e1, termsSelection, termsCreation, selection));

        } catch (NullPointerException ex) {
            contractRenewalView.getErrorLabel().setText("No contracts selected");
        }
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

    private void handleOldTerms(ActionEvent e, TermsSelection termsSelection) {
        try {
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

        } catch (NullPointerException ex) {
            contractRenewalView.getErrorLabel().setText("No contracts selected");
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
