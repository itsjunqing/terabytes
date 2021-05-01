package api;

import stream.Contract;

import java.util.List;

public class ContractApi extends BasicApi<Contract> implements ApiInterface<Contract> {

    private final String CONTRACT_ENDPOINT = "/contract";
    private final String SIGN_ENDPOINT = "/sign";

    @Override
    public List<Contract> getAll() {
        return getAllObjects(CONTRACT_ENDPOINT, Contract[].class);
    }

    @Override
    public Contract get(String id) {
        return getObject(CONTRACT_ENDPOINT + "/" + id, Contract.class);
    }

    @Override
    public Contract add(Contract object) {
        return postObject(CONTRACT_ENDPOINT, object, Contract.class);
    }

    @Override
    public boolean patch(String id, Contract object) {
        return patchObject(CONTRACT_ENDPOINT + "/" + id, object);
    }

    @Override
    public boolean remove(String id) {
        return deleteObject(CONTRACT_ENDPOINT + "/" + id);
    }

    public boolean sign(String id, Contract object) {
        return postObject(CONTRACT_ENDPOINT + "/" + id + SIGN_ENDPOINT, object);
    }

    /*
    public boolean signContract(String id, Contract object) {
        return postObject(CONTRACT_ENDPOINT + "/" + id + SIGN_ENDPOINT, object);
    }


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

    public boolean patchContract(String id, Contract contract) {
        String endpoint = CONTRACT_ENDPOINT + "/" + id;
        return patchObject(endpoint, contract);
    }

    public boolean removeContract(String id) {
        return deleteObject(CONTRACT_ENDPOINT + "/" + id);
    }
    */

}
