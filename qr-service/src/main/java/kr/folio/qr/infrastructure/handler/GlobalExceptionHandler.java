package kr.folio.qr.infrastructure.handler;

import jakarta.validation.ConstraintViolationException;
import kr.folio.qr.infrastructure.exception.CustomException;
import kr.folio.qr.infrastructure.exception.ErrorCode;
import kr.folio.qr.infrastructure.exception.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException exception) {
        return ResponseEntity
            .badRequest()
            .body(ErrorResponse.fromErrorCode(exception.getErrorCode()));
    }

    @ExceptionHandler({ConstraintViolationException.class,
        MethodArgumentNotValidException.class,
        WebExchangeBindException.class})
    public ResponseEntity<ErrorResponse> validException(Exception ex) {
        String errorMessage = "입력값 검증 오류: ";
        if (ex instanceof MethodArgumentNotValidException mex) {
            errorMessage += mex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        } else if (ex instanceof WebExchangeBindException wex) {
            errorMessage += wex.getAllErrors().get(0).getDefaultMessage();
        } else {
            errorMessage += "알 수 없는 오류";
        }
        ErrorResponse response = new ErrorResponse(
            ErrorCode.REQUEST_INPUT_NOT_VALID.getCode(),
            errorMessage
        );

        return ResponseEntity
            .badRequest()
            .body(response);
    }

}
