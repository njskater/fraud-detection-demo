package fraud.detection.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fraud.detection.model.Transaction;

/**
 * A utility class to help build transaction model from transaction record string.
 * @author jzhang
 */
public class TransactionBuilder {
    private static final Log LOGGER = LogFactory.getLog(TransactionBuilder.class);
    private static final String COMMA = ",";
    private static final String TRANSACTION_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    /**
     * Build a Transaction object from transaction record String.
     * @param transactionRecord transaction record as a comma separated string:
     * <ul>
     * <li>hashed credit card number</li>
     * <li>timestamp - of format 'year-month-dayThour:minute:second'</li>
     * <li>price - of format 'dollars.cents'</li>
     * </ul>
     * @return A new {@link Transaction} object based on given transaction record.
     */
    public Transaction build(String transactionRecord) {
        if (transactionRecord == null) {
            return null;
        }

        final String[] fields = transactionRecord.split(COMMA);
        if (fields.length != 3) {
            LOGGER.warn("Malformatted transaction record:" + transactionRecord);
            return null;
        }

        final Date transactionDate;
        try {
            transactionDate = DateUtils.truncate(
                    DateUtils.parseDate(fields[1].trim(), TRANSACTION_DATE_FORMAT), Calendar.DATE);
        } catch (final ParseException e) {
            LOGGER.warn("Invalid transaction date in record:" + transactionRecord);
            return null;
        }

        final BigDecimal amount;
        try {
            amount = new BigDecimal(fields[2].trim());
        } catch (final NumberFormatException e) {
            LOGGER.warn("Invalid transaction amount in record:" + transactionRecord);
            return null;
        }

        return new Transaction(fields[0].trim(), transactionDate, amount);
    }
}
