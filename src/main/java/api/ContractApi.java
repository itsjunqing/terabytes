package api;

import stream.Contract;

import java.util.List;

public class ContractApi extends BasicApi<Contract> {

    private final String CONTRACT_ENDPOINT = "/contract";

    public List<Contract> getAllContracts() {
        return getAllObjects(CONTRACT_ENDPOINT, Contract[].class);
    }

    public Contract getContract(String id) {
        String endpoint = CONTRACT_ENDPOINT + "/" + id;
        return getObject(endpoint, Contract.class);
    }

    public Contract addContract(Contract contract) {
        return postObject(CONTRACT_ENDPOINT, contract, Contract.class);
    }

    public boolean patchContract(Contract contract) {
        return patchObject(CONTRACT_ENDPOINT, contract);
    }

    public boolean removeContract(String id) {
        return deleteObject(CONTRACT_ENDPOINT + "/" + id);
    }

    public boolean signContract(String id, Contract contract) {
        String endpoint = CONTRACT_ENDPOINT + "/" + id + "/sign";
        return postObject(endpoint, contract);
    }
}
