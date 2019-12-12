package rs.ac.uns.ftn.ktsnwt.exception;

public class BadAttributeValueException extends RuntimeException{

    public BadAttributeValueException(String message) {
        super(message);
    }

    public BadAttributeValueException(String message, Throwable cause) {
        super(message, cause);
    }

}
