package rs.ac.uns.ftn.ktsnwt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(value = {ApiRequestException.class})
    public ResponseEntity<ErrorMessage> handleApiRequestException(ApiRequestException e) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ErrorMessage errorMessage = new ErrorMessage(e.getMessage(), badRequest);
        return new ResponseEntity<>(errorMessage, badRequest);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorMessage> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        ErrorMessage errorMessage = new ErrorMessage(message, badRequest);
        return new ResponseEntity<>(errorMessage, badRequest);
    }

    @ExceptionHandler(value = {ResourceNotFoundException.class, EventDayNotFoundException.class})
    public ResponseEntity<ErrorMessage> handleResourceNotFoundException(Exception e) {
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        ErrorMessage errorMessage = new ErrorMessage(e.getMessage(), notFound);
        return new ResponseEntity<>(errorMessage, notFound);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ErrorMessage> handleException(Exception e) {
        HttpStatus internalServerError = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorMessage errorMessage = new ErrorMessage("There was an internal server error.", internalServerError);
        e.printStackTrace();

        return new ResponseEntity<>(errorMessage, internalServerError);
    }


    @ExceptionHandler(value = {ResourceAlreadyExistsException.class})
    public ResponseEntity<ErrorMessage> handleResourceAlreadyExistsException(ResourceAlreadyExistsException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorMessage errorMessage = new ErrorMessage(e.getMessage(), status);
        e.printStackTrace();

        return new ResponseEntity<>(errorMessage, status);
    }

    @ExceptionHandler(value = {BadAttributeValueException.class})
    public ResponseEntity<ErrorMessage> handleBadAttributeValueException(BadAttributeValueException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorMessage errorMessage = new ErrorMessage(e.getMessage(), status);
        e.printStackTrace();

        return new ResponseEntity<>(errorMessage, status);
    }

}
