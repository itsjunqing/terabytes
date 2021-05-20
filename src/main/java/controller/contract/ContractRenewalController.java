package controller.contract;

import controller.EventListener;
import model.contract.ContractRenewalModel;
import strategy.RenewByNewTerms;
import strategy.RenewByOldTerms;
import strategy.RenewStrategy;
import view.contract.ContractRenewalView;
import view.contract.ContractSelection;

import java.awt.event.ActionEvent;
import java.util.HashMap;

/**
 * A class of ContractRenewalController to handle the actions of renewing contracts
 */
public class ContractRenewalController implements EventListener {

    private ContractRenewalModel contractRenewalModel;
    private ContractRenewalView contractRenewalView;
    private HashMap<String, RenewStrategy> renewStrategies;

    /**
     * Constructs a ContractRenewalController
     * @param userId a String of user id
     */
    public ContractRenewalController(String userId) {
        this.contractRenewalModel = new ContractRenewalModel(userId);
        this.contractRenewalView = new ContractRenewalView(contractRenewalModel);
        this.contractRenewalModel.attach(contractRenewalView);
        this.renewStrategies = new HashMap<>();
        renewStrategies.put("new", new RenewByNewTerms(contractRenewalModel));
        renewStrategies.put("old", new RenewByOldTerms(contractRenewalModel));
        listenViewActions();
    }

    /**
     * Listen to dashboard actions
     */
    @Override
    public void listenViewActions() {
        contractRenewalView.getRefreshButton().addActionListener(this::handleRefresh);
        contractRenewalView.getRenewContractButton().addActionListener(this::handleRenew);
    }

    /**
     * Handles dashboard refreshing
     */
    private void handleRefresh(ActionEvent e) {
        contractRenewalModel.refresh();
    }

    /**
     * Handles contract renewing action
     */
    private void handleRenew(ActionEvent e) {
        if (contractRenewalModel.getExpiredContracts().size() == 0) {
            contractRenewalView.getErrorLabel().setText("There is no expired contracts, can't renew");

        } else if (contractRenewalModel.getActiveContracts().size() == 5) {
            contractRenewalView.getErrorLabel().setText("Error, 5 contracts are already in active!");

        } else {
            ContractSelection contractSelection = new ContractSelection(contractRenewalModel.getAllContracts());
            try {
                contractSelection.getRenewNewTermsButton().addActionListener(e1 -> {
                    renewStrategies.get("new").renew(contractSelection);
                });
                contractSelection.getRenewOldTermsButton().addActionListener(e1 -> {
                    renewStrategies.get("old").renew(contractSelection);
                });
            } catch (NullPointerException ex) {
                contractRenewalView.getErrorLabel().setText("No contracts selected");
            }
        }
    }

}




/*
    ContractSelection termsSelection = new ContractSelection(contractRenewalModel.getAllContracts());
        termsSelection.getRenewNewTermsButton().addActionListener(e1 -> handleNewTerms(e1, termsSelection));
                termsSelection.getRenewOldTermsButton().addActionListener(e1 -> handleOldTerms(e1, termsSelection));
private void handleNewTerms(ActionEvent e, ContractSelection termsSelection) {
        try {
        int selection = termsSelection.getContractSelection();

        TermsCreation termsCreation = new TermsCreation();
        termsCreation.getSubmitNewTermsButton().addActionListener(e1 ->
        renewNewTerms(e1, termsSelection, termsCreation, selection));

        } catch (NullPointerException ex) {
        contractRenewalView.getErrorLabel().setText("No contracts selected");
        }
        }

private void renewNewTerms(ActionEvent e, ContractSelection termsSelection,
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

private void handleOldTerms(ActionEvent e, ContractSelection termsSelection) {
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
*/
