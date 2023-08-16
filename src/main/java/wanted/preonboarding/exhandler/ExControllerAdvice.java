package wanted.preonboarding.exhandler;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@RestControllerAdvice(basePackages = "wanted.preonboarding.controller")
public class ExControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResult> validRequestException(MethodArgumentNotValidException ex){

        BindingResult result = ex.getBindingResult();
        FieldError fieldError = result.getFieldError();
        HttpStatusCode statusCode = ex.getStatusCode();
        String defaultMessage = fieldError.getDefaultMessage();

        ErrorResult errorResult = new ErrorResult(statusCode.toString(), defaultMessage);

        return new ResponseEntity<>(errorResult, statusCode);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResult> illegalExhandler(IllegalArgumentException ex){

        ErrorResult errorResult = new ErrorResult(HttpStatus.BAD_REQUEST.toString(), ex.getMessage());

        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResult> badCredentialEx(BadCredentialsException ex){

        ErrorResult errorResult = new ErrorResult(HttpStatus.BAD_REQUEST.toString(), "이메일 혹은 비밀번호가 일치하지 않습니다.");

        return ResponseEntity.badRequest().body(errorResult);
    }
}
