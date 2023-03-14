package az.enjooy.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AppExceptionHandler {

    private static final Logger logger = LogManager.getLogger(AppExceptionHandler.class);


    @ExceptionHandler(value = {NoSuchAccountException.class})
    public ResponseEntity<Object> handleNoSuchAccountException(Exception ex) {
        ErrorMessage errorMessage = new ErrorMessage(
                HttpStatus.NOT_ACCEPTABLE, ex.getLocalizedMessage(), "No such account registered");
        logger.error(ex);
        ex.printStackTrace();
        return new ResponseEntity<>(
                errorMessage, new HttpHeaders(), errorMessage.getStatus());
    }

    @ExceptionHandler(value = {InvalidPasswordException.class})
    public ResponseEntity<Object> handleInvalidPasswordException(Exception ex) {
        ErrorMessage errorMessage = new ErrorMessage(
                HttpStatus.UNAUTHORIZED, ex.getLocalizedMessage(), "Invalid Password");
        logger.error(ex);
        ex.printStackTrace();
        return new ResponseEntity<>(
                errorMessage, new HttpHeaders(), errorMessage.getStatus());
    }
}