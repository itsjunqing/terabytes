package service;

import api.*;

public class ApiService {

    private UserApi userApi;
    private SubjectApi subjectApi;
    private ContractApi contractApi;
    private BidApi bidApi;
    private MessageApi messageApi;

    public ApiService() {
        this.userApi = new UserApi();
        this.subjectApi = new SubjectApi();
        this.contractApi = new ContractApi();
        this.bidApi = new BidApi();
        this.messageApi = new MessageApi();
    }

    public void runVerify() {

    }


}
