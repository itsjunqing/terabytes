package service;

import api.*;

public class ApiService {

    public static UserApi userApi = new UserApi();
    public static SubjectApi subjectApi = new SubjectApi();
    public static ContractApi contractApi = new ContractApi();
    public static BidApi bidApi = new BidApi();
    public static MessageApi messageApi = new MessageApi();
    public static CompetencyApi competencyApi = new CompetencyApi();
    public static QualificationApi qualificationApi = new QualificationApi();

//    private static UserApi userApi;
//
//    public static UserApi userApi() {
//        if (userApi == null) {
//            userApi = new UserApi();
//        }
//        return userApi;
//    }
}
