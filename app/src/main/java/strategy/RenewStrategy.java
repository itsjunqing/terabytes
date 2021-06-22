package strategy;

import view.form.ContractSelection;

/**
 * An interface of RenewStrategy
 */
public interface RenewStrategy {

    void renew(ContractSelection contractSelection) throws NullPointerException;
}
