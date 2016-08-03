package fraud.detection;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.time.DateUtils;

import fraud.detection.model.Transaction;
import fraud.detection.util.BigDecimalUtil;
import fraud.detection.util.TransactionBuilder;

/**
 * A demo application to show a credit card fraud detection algorithm.
 * @author jzhang
 *
 */
public class DemoApplication {

    /**
     * Detect credit card fraud based on following rule:
     * A credit card will be identified as fraudulent if the sum of prices for a
     * unique hashed credit card number, for a given day, exceeds prices threshold.
     * @param transactions transaction records as a list of comma separated string:
     *      <ul>
     *        <li>hashed credit card number</li>
     *        <li>timestamp - of format 'year-month-dayThour:minute:second'</li>
     *        <li> price - of format 'dollars.cents'</li>
     *      </ul>
     * @param date the date base to detect fraud.
     * @param threshold the threshold price to be identified as fraud.
     * @return List of hashed credit card numbers that is identified as fraud.
     */
    public static List<String> detectFraud(List<String> transactions, Date date, BigDecimal threshold) {
        if (transactions == null || date == null | threshold == null) {
            throw new IllegalArgumentException("parameter transactions, date and threshold must be provided");
        }

        final Date truncDate = DateUtils.truncate(date, Calendar.DATE);

        final TransactionBuilder builder = new TransactionBuilder();

        //Run stream map reduce
        return transactions.parallelStream()
                //1. transform to object
                .map(s -> builder.build(s))
                //2. filter by not null transaction and date
                .filter(tx -> tx != null && truncDate.equals(tx.getTransactionDate()))
                //3. Group by credit card number and sum transaction amount
                .collect(Collectors.groupingBy(Transaction::getCreditCardNumber,
                        Collectors.reducing(BigDecimal.ZERO, Transaction::getAmount, BigDecimalUtil::sum)))
                        .entrySet()
                        .parallelStream()
                        //4. filter by threshold
                        .filter(entry -> entry.getValue().compareTo(threshold) > 0)
                        //5. convert back to list
                        .map(entry -> entry.getKey())
                        .collect(Collectors.toList());
    }




}
