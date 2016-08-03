package fraud.detection;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Unit Test for {@link DemoApplication}. This is also a show case for
 * fraud detection algorithm.
 * @author jzhang
 *
 */
public class DemoApplicationTest {
    private static final String INPUT_DATE_FORMAT = "yyyy-MM-dd";
    private final List<String> transactionRecord = Arrays.asList(
            //01-MAY
            "10d7ce2f43e35fa57d1bbf8b1e2, 2014-05-01T13:15:54, 10.00",
            "10d7ce2f43e35fa57d1bbf8b1e2, 2014-05-01T14:15:54, 20.00",
            "10d7ce2f43e35fa57d1bbf8b1e2, 2014-05-01T15:15:54, 20000.00",
            "some malformed data,abd,ddfsfs",

            "20d7ce2f43e35fa57d1bbf8b1e2, 2014-05-01T13:15:54, 10.00",

            "30d7ce2f43e35fa57d1bbf8b1e2, 2014-05-01T13:16:54, 10.00",

            "40d7ce2f43e35fa57d1bbf8b1e2, 2014-05-01T13:17:54, 10.00",

            "50d7ce2f43e35fa57d1bbf8b1e2, 2014-05-01T13:15:54, 10.00",
            "50d7ce2f43e35fa57d1bbf8b1e2, 2014-05-01T13:15:55, 70.00",
            "50d7ce2f43e35fa57d1bbf8b1e2, 2014-05-01T13:15:56, 6090.00",
            "50d7ce2f43e35fa57d1bbf8b1e2, 2014-05-01T13:15:57, 6000.00",


            //02-MAY
            "10d7ce2f43e35fa57d1bbf8b1e2, 2014-05-02T13:15:54, 20.00",

            "20d7ce2f43e35fa57d1bbf8b1e2, 2014-05-02T14:15:54, 90.00",
            "20d7ce2f43e35fa57d1bbf8b1e2, 2014-05-02T15:15:54, 900.00",
            "some other malformed data,abd,ddfsfs",
            "20d7ce2f43e35fa57d1bbf8b1e2, 2014-05-02T16:15:54, 9000.00",
            "20d7ce2f43e35fa57d1bbf8b1e2, 2014-05-02T17:15:54, 1000.00",

            "30d7ce2f43e35fa57d1bbf8b1e2, 2014-05-02T18:15:54, 10.00",

            "40d7ce2f43e35fa57d1bbf8b1e2, 2014-05-02T19:15:54, 10.00",

            "50d7ce2f43e35fa57d1bbf8b1e2, 2014-05-02T12:15:54, 10.00",

            //03-MAY
            "10d7ce2f43e35fa57d1bbf8b1e2, 2014-05-03T13:15:54, 30.00",
            "20d7ce2f43e35fa57d1bbf8b1e2, 2014-05-03T13:15:55, 10.00",
            "30d7ce2f43e35fa57d1bbf8b1e2, 2014-05-03T13:15:56, 10.00",
            "40d7ce2f43e35fa57d1bbf8b1e2, 2014-05-03T13:15:57, 10.00",
            "50d7ce2f43e35fa57d1bbf8b1e2, 2014-05-03T13:15:58, 10.00"
            );

    private List<String> runTest(final String dateAsStr, final String thresholdAsStr) throws ParseException {
        final Date targetDate = DateUtils.parseDate(dateAsStr, INPUT_DATE_FORMAT);
        final BigDecimal threshold = new BigDecimal(thresholdAsStr);

        final List<String> result = DemoApplication.detectFraud(transactionRecord, targetDate, threshold);
        return result;
    }

    @Test
    public void testReachThreshold() throws Exception {
        final List<String> result = runTest("2014-05-01", "10000.00");

        assertEquals("Expect 2 credit cards in fraud list", 2, result.size());
        assertTrue("Expect 1st credit card number in fraud list", result.contains("10d7ce2f43e35fa57d1bbf8b1e2"));
        assertTrue("Expect 1st credit card number in fraud list", result.contains("50d7ce2f43e35fa57d1bbf8b1e2"));
    }


    @Test
    public void testReachThreshold2() throws Exception {
        final List<String> result = runTest("2014-05-01", "20000.00");

        assertEquals("Expect 1 credit card in fraud list", 1, result.size());
        assertTrue("Expect 1st credit card number in fraud list", result.contains("10d7ce2f43e35fa57d1bbf8b1e2"));
    }

    @Test
    public void testReachThreshold3() throws Exception {
        final List<String> result = runTest("2014-05-02", "10000.00");

        assertEquals("Expect 1 credit card in fraud list", 1, result.size());
        assertTrue("Expect 2nd credit card number in fraud list", result.contains("20d7ce2f43e35fa57d1bbf8b1e2"));
    }

    @Test
    public void testNotReachThreshold() throws Exception {
        final List<String> result = runTest("2014-05-03", "10000.00");

        assertEquals("Expect no credit card in fraud list", 0, result.size());
    }
}
