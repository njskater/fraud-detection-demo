package fraud.detection.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * A POJO class to represent transaction model.
 * @author jzhang
 *
 */
public class Transaction {
    private final String creditCardNumber;
    private final Date transactionDate;
    private final BigDecimal amount;

    public Transaction(String creditCardNumber, Date transactionDate, BigDecimal amount) {
        super();
        this.creditCardNumber = creditCardNumber;
        this.transactionDate = transactionDate;
        this.amount = amount;
    }


    public BigDecimal getAmount() {
        return amount;
    }


    public String getCreditCardNumber() {
        return creditCardNumber;
    }


    public Date getTransactionDate() {
        return transactionDate;
    }
}
