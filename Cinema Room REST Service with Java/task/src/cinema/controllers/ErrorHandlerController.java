package cinema.controllers;

import cinema.entities.CustomErrorMessage;
import cinema.exceptions.IncorrectPasswordException;
import cinema.exceptions.SeatAlreadyPurchasedException;
import cinema.exceptions.SeatRowColumnOutOfBound;
import cinema.exceptions.WrongTokenException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class ErrorHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler({
            SeatAlreadyPurchasedException.class,
            SeatRowColumnOutOfBound.class,
            WrongTokenException.class,
            IncorrectPasswordException.class
    })
    public ResponseEntity<CustomErrorMessage> handleBadRequestExceptions(RuntimeException e) {
        return ResponseEntity.badRequest().body(CustomErrorMessage.of(e.getMessage()));
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> error = Map.of("error", "The %s is wrong!".formatted(ex.getParameterName()));
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(error);
    }
}
