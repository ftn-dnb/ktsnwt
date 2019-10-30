package rs.ac.uns.ftn.ktsnwt.exception;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class ErrorMessage {

    private final String message;
    private final HttpStatus httpStatus;
    private final ZonedDateTime timestamp;

    public ErrorMessage(String message, HttpStatus status) {
        this.message = message;
        this.httpStatus = status;
        this.timestamp = ZonedDateTime.now();
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }
}
