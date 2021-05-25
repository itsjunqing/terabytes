package controller.contract;

import controller.EventListener;
import model.contract.ContractRenewalModel;
import strategy.RenewByNewTerms;
import strategy.RenewByOldTerms;
import strategy.RenewStrategy;
import view.contract.ContractRenewalView;
import view.form.ContractSelection;

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
