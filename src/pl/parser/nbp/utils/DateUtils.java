package pl.parser.nbp.utils;

import java.time.LocalDate;

/**
 * Utility methods for operations on dates
 * @author Michał Lal
 */
public class DateUtils {

    /**
     * Checks if given date is within range of dates (inclusively)
     * @param date checked date
     * @param startDate start date of range
     * @param endDate end date of range
     * @return <b>true</b> - if date is within range<br/>
     * <b>false</b> - otherwise
     */
    public static boolean isInDateRange(LocalDate date, LocalDate startDate, LocalDate endDate) {
        return !(date.isBefore(startDate) || date.isAfter(endDate));
    }
}
