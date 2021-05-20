package strategy;

import view.contract.ContractSelection;

/**
 * An interface of RenewStrategy
 */
public interface RenewStrategy {
    void renew(ContractSelection contractSelection) throws NullPointerException;
}
