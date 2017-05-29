package pl.parser.nbp.validation;

/**
 * Exception thrown during input data validation
 * @author Michał Lal
 */
public class ValidationException extends Exception {

    public ValidationException(String message) {
        super(message);
    }
}
