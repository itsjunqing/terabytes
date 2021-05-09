package stream;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * A Payment data class.
 */
@Data @AllArgsConstructor
public class Payment {
    private Integer totalPrice;

    /**
     * Copy constructor that copies the payment object.
     * @param payment a Payment object
     */
    public Payment(Payment payment) {
        this.totalPrice = payment.totalPrice;
    }
}
