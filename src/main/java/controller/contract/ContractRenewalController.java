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
import java.util.List;

public class ContractRenewalController implements EventListener {

    private ContractRenewalModel contractRenewalModel;
    private ContractRenewalView contractRenewalView;

    public ContractRenewalController(String userId) {
        this.contractRenewalModel = new ContractRenewalModel(userId);
        this.contractRenewalView = new ContractRenewalView(contractRenewalModel);
        listenViewActions();
    }

    @Override
    public void listenViewActions() {
        contractRenewalView.getRefreshButton().addActionListener(this::handleRefresh);
        contractRenewalView.getRenewNewTermsButton().addActionListener(this::handleNewTerms);
        contractRenewalView.getRenewOldTermsButton().addActionListener(this::handleExistingTerms);
    }

    private void handleRefresh(ActionEvent e) {
        contractRenewalModel.refresh();
    }

    private void handleNewTerms(ActionEvent e) {
        try {
            int selection = contractRenewalView.getContractSelectionBox();

            // create terms if active contracts less than 5
            if (contractRenewalModel.getActiveContracts().size() < 5) {
                TermsCreation form = new TermsCreation();
                form.getSubmitNewTermsButton().addActionListener(e1 -> renewNewTerms(e1, form, selection));
            } else {
                contractRenewalView.getErrorLabel().setText("Error, 5 contracts are already active");
            }

        } catch (NullPointerException ex) {
            contractRenewalView.getErrorLabel().setText("No contract selected!");
        }
    }

    private void handleExistingTerms(ActionEvent e) {
        try {
            int selection = contractRenewalView.getContractSelectionBox();
            // create terms if active contracts less than 5
            if (contractRenewalModel.getActiveContracts().size() < 5) {
                TermsSelection form = new TermsSelection(contractRenewalModel);
                form.getSelectButton().addActionListener(e1 -> renewExistingTerms(e1, form, selection));
            } else {
                contractRenewalView.getErrorLabel().setText("Error, 5 contracts are already active");
            }

        } catch (NullPointerException ex) {
            contractRenewalView.getErrorLabel().setText("No contract selected!");
        }
    }

    private void renewNewTerms(ActionEvent e, TermsCreation form, int selection) {
        try {
            BidInfo newTerms = extractNewTerms(form);
            System.out.println("From ContractRenewalController: Extracted: " + newTerms);
            Contract contract = contractRenewalModel.renewNewTerms(selection, newTerms);
            form.dispose();

            new ContractConfirmController(contract, ViewUtility.STUDENT_CODE, false);
        } catch (NullPointerException ex) {
            System.out.println("Incomplete form found");
            form.getErrorLabel().setText("Incomplete form!");
        }
    }

    private void renewExistingTerms(ActionEvent e, TermsSelection form, int selection) {
        List<String> tutorsUserNames = contractRenewalModel.getTutorsList(selection);
        if (!tutorsUserNames.isEmpty()) {
            // When there is a tutor in the list, dispose the TermsSelection form
            form.dispose();

            // Construct TutorSelection form to get tutor option
            TutorSelection tutorSelection = new TutorSelection(tutorsUserNames);
            tutorSelection.getSelectTutorButton().addActionListener(e1 -> {
                String tutorUsername = tutorSelection.getTutorOption();
                Contract contract = contractRenewalModel.renewExistingTerms(selection, tutorUsername);
                tutorSelection.dispose(); // dispose when option is selected

                new ContractConfirmController(contract, ViewUtility.STUDENT_CODE, false);
            });
        } else {
            form.getErrorLabel().setText("No tutors have the required competency, please try another renewal");
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


//    private void handleExistingTerms() {
//        /**
//         * 1. Get the selected expired contract
//         * 2. Create ExistingTermsView and pass (all contracts except expiredContract) + expiredContract
//         * 3. Listen to Select button
//         * 4. Get the Contract selected
//         * 5. Get the tutor list of the selected contract to be renewed
//         * 6. If list empty -> prompt error
//         * 7. If list has tutor -> create Trash view and pass the list
//         * 8. Listen to Select button
//         * 9. Get the username of the tutor selected
//         * 10. Get the User of the selected username
//         * 11. Create Contract object
//         * 12. Create ContractConfirmation Form
//         */
//
//        Contract selectedContract = null;
//        User tutor = Utility.getUser("someTutorUsername");
//        Contract contract = BuilderService.buildContract(selectedContract, tutor.getId());
//
//
//    }