package service;

import api.*;

/**
 * Service class to access all API endpoints
 * Singleton is used to prevent unnecessary object creations
 */
public class ApiService {

    private static UserApi userApi;
    private static SubjectApi subjectApi;
    private static ContractApi contractApi;
    private static BidApi bidApi;
    private static MessageApi messageApi;
    private static CompetencyApi competencyApi;
    private static QualificationApi qualificationApi;

    public static UserApi userApi() {
        if (userApi == null) {
            userApi = new UserApi();
        }
        return userApi;
    }

    public static SubjectApi subjectApi() {
        if (subjectApi == null) {
            subjectApi = new SubjectApi();
        }
        return subjectApi;
    }

    public static ContractApi contractApi() {
        if (contractApi == null) {
            contractApi = new ContractApi();
        }
        return contractApi;
    }

    public static BidApi bidApi() {
        if (bidApi == null) {
            bidApi = new BidApi();
        }
        return bidApi;
    }

    public static MessageApi messageApi() {
        if (messageApi == null) {
            messageApi = new MessageApi();
        }
        return messageApi;
    }

    public static CompetencyApi competencyApi() {
        if (competencyApi == null) {
            competencyApi = new CompetencyApi();
        }
        return competencyApi;
    }

    public static QualificationApi qualificationApi() {
        if (qualificationApi == null) {
            qualificationApi = new QualificationApi();
        }
        return qualificationApi;
    }

}
