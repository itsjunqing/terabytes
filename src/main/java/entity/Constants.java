package entity;

import java.util.Calendar;

/**
 * A Constants data class.
 */
public final class Constants {

    public final static int OPEN_BID_MINS = 3000;
    public final static int CLOSE_BID_DAYS = 7;

    public final static int LOGIN_EXPIRY_MONTHS = 1;

    // constant of contract add type (month) and its default duration is set here
    // modify it to perform manual testing
//    public final static int DEFAULT_CONTRACT_ADD_TYPE = Calendar.MONTH;
//    public final static int DEFAULT_CONTRACT_DURATION = 6; // months

    public final static int DEFAULT_CONTRACT_ADD_TYPE = Calendar.MINUTE;
    public final static int DEFAULT_CONTRACT_DURATION = 10; // expire after 10 mins

}
