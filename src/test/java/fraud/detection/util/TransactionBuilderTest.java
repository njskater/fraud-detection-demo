package fraud.detection.util;

import java.math.BigDecimal;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Before;
import org.junit.Test;

import fraud.detection.model.Transaction;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Unit test for {@link TransactionBuilder}.
 * @author jzhang
 *
 */
public class TransactionBuilderTest {
    private TransactionBuilder builder;

    @Before
    public void setup() {
        builder = new TransactionBuilder();
    }

    @Test
    public void testNullTransactionRecord() {
        assertNull(builder.build(null));
    }

    @Test
    public void testMalformedTransactionRecord() {
        assertNull(builder.build("Malformed Transaction Record"));
    }

    @Test
    public void testInvalidTransactionDateFormat() {
        assertNull(builder.build("10d7ce2f43e35fa57d1bbf8b1e2, 2014-APR-29T13:15:54, 10.00"));
    }

    @Test
    public void testInvalidTransactionAmount() {
        assertNull(builder.build("10d7ce2f43e35fa57d1bbf8b1e2, 2014-04-29T13:15:54, abcd"));
    }

    @Test
    public void testValidTransactionRecord() throws Exception {
        final Transaction transaction = builder.build("10d7ce2f43e35fa57d1bbf8b1e2, 2014-04-29T13:15:54, 10.00");
        assertEquals("10d7ce2f43e35fa57d1bbf8b1e2", transaction.getCreditCardNumber());
        assertEquals(DateUtils.parseDate("2014-04-29", "yyyy-MM-dd"), transaction.getTransactionDate());
        assertEquals(new BigDecimal("10.00"), transaction.getAmount());
    }
}
