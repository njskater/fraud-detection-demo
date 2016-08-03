package fraud.detection.util;

import java.math.BigDecimal;

/**
 * A Utility Class to help BigDecimal Operations.
 * @author jzhang
 *
 */
public class BigDecimalUtil {
    /**
     * Sum two BigDecimal
     * @param b1
     * @param b2
     * @return sum of two BigDecimal.
     */
    public static BigDecimal sum(BigDecimal b1, BigDecimal b2) {
        return b1.add(b2);
    }
}
