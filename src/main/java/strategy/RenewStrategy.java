package strategy;

import view.form.TermsSelection;

public interface RenewStrategy {
    void renew(TermsSelection termsSelection) throws NullPointerException;
}
