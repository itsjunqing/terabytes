package model.contract;

import lombok.Getter;
import service.ApiService;
import stream.Contract;

import java.util.Calendar;
import java.util.Date;

@Getter
public class ContractConfirmModel {

    // Note: this contract is the object yet to be pushed to the API (consists of firstPartyId and not firstParty)
    private Contract contract;
    private int type;
    private boolean sign;

    public ContractConfirmModel(Contract contract, int type, boolean sign) {
        this.type = type;
        this.sign = sign;
        this.contract = contract;
    }

    private void createContract(Contract contract) {
        // post to API and update the contract attribute to be the (posted) contract
        this.contract = ApiService.contractApi().add(contract);
    }

    public void updateExpiry(int months) {
        // calculate expiry date based on date creation
        Calendar c = Calendar.getInstance();
        c.setTime(contract.getDateCreated());
        c.add(Calendar.MONTH, months);
        Date expiryDate = c.getTime();

        // update expiry date
        contract.setExpiryDate(expiryDate);
    }

    public void signContract() {
        // push to API and sign
        Contract contractAdded = ApiService.contractApi().add(contract);
        String contractId = contractAdded.getId();
        ApiService.contractApi().sign(contractId, new Contract(new Date()));
    }
}
