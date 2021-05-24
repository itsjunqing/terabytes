package service;

import builder.BidBuilder;
import builder.ContractBuilder;

/**
 * A builder class that builds objects to be re-used.
 */
public class BuilderService {

    private static BidBuilder bidBuilder;
    private static ContractBuilder contractBuilder;

    public static BidBuilder bidBuilder() {
        if (bidBuilder == null) {
            bidBuilder = new BidBuilder();
        }
        return bidBuilder;
    }

    public static ContractBuilder contractBuilder() {
        if (contractBuilder == null) {
            contractBuilder = new ContractBuilder();
        }
        return contractBuilder;
    }
}

