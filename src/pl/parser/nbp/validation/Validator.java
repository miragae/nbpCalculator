package pl.parser.nbp.validation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Michał Lal
 */
public class Validator {

    /**
     * Checks if dates are:
     * <ul>
     * <li>not null</li>
     * <li>valid</li>
     * <li>in correct format</li>
     * <li>in correct order</li>
     * <li> after the minimum date</li>
     * </ul>
     * @param startDateString string with entered start date
     * @param endDateString string with entered end date
     * @param dateFormat required date format
     * @param minimumDate minimum possible date
     * @throws ValidationException when dates are invalid
     */
    public static void validateDateStrings(String startDateString, String endDateString, DateTimeFormatter dateFormat, LocalDate minimumDate) throws ValidationException {
        try {
            validateDateString(startDateString, dateFormat);
        } catch (ValidationException ex) {
            throw new ValidationException("Entered start date is invalid");
        }
        try {
            validateDateString(endDateString, dateFormat);
        } catch (ValidationException ex) {
            throw new ValidationException("Entered end date is invalid");
        }
        LocalDate startDate = LocalDate.parse(startDateString, dateFormat);
        LocalDate endDate = LocalDate.parse(endDateString, dateFormat);
        validateDateOrder(startDate, endDate);
        validateMinimumDate(startDate, minimumDate);
    }

    /**
     * Checks if dates are not null and in correct order
     * @param startDate parsed start date
     * @param endDate parsed end date
     * @throws ValidationException when dates are in wrong order (start date is after end date)
     */
    public static void validateDateOrder(LocalDate startDate, LocalDate endDate) throws ValidationException {
        if (startDate == null) {
            throw new ValidationException("Entered start date is invalid");
        }
        if (endDate == null) {
            throw new ValidationException("Entered end date is invalid");
        }
        if (startDate.isAfter(endDate)) {
            throw new ValidationException("Dates are in wrong order");
        }
    }

    /**
     * Checks if date is not null and in correct format
     * @param date entered date string
     * @param dateFormat required date format
     * @throws ValidationException when date is null or invalid
     */
    public static void validateDateString(String date, DateTimeFormatter dateFormat) throws ValidationException {
        try {
            LocalDate.parse(date, dateFormat);
        } catch (Exception ex) {
            throw new ValidationException("Entered date is invalid");
        }
    }

    /**
     * Checks if currency matches required pattern
     * @param currency entered currency
     * @param pattern required currency pattern
     * @throws ValidationException when currency is invalid
     */
    public static void validateCurrency(String currency, String pattern) throws ValidationException {
        if (currency == null || !currency.matches(pattern)) {
            throw new ValidationException("Entered currency code is invalid");
        }
    }

    /**
     * Checks if date is after minimum date
     * @param date checked date
     * @param minimumDate minimum possible date
     * @throws ValidationException when checked date is after minimum date
     */
    public static void validateMinimumDate(LocalDate date, LocalDate minimumDate) throws ValidationException {
        if (minimumDate.isAfter(date)) {
            throw new ValidationException("2012-01-01 is the earliest possible date");
        }
    }
}
