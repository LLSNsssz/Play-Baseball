package org.example.spring.common;

import org.example.spring.exception.AccountBannedException;
import org.example.spring.exception.AccountDeletedException;
import org.example.spring.exception.InvalidCredentialsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponseDto<Void>> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ApiResponseDto.error("요청에 실패했습니다: " + e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDto<Void>> handleException(Exception e) {
        log.error("서버 오류 발생: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ApiResponseDto.error("서버 오류가 발생했습니다."));
    }

    @ExceptionHandler(AccountDeletedException.class)
    public ResponseEntity<ApiResponseDto<Void>> handleAccountDeletedException(AccountDeletedException ex) {
        log.warn("Attempt to access deleted account: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
            .body(ApiResponseDto.error("이 계정은 삭제 되었습니다"));
    }

    @ExceptionHandler(AccountBannedException.class)
    public ResponseEntity<ApiResponseDto<Void>> handleAccountBannedException(AccountBannedException ex) {
        log.warn("Attempt to access banned account: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
            .body(ApiResponseDto.error("이 계정은 밴 되었습니다"));
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ApiResponseDto<Void>> handleInvalidCredentialsException(InvalidCredentialsException ex) {
        log.warn("Invalid credentials: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(ApiResponseDto.error("Invalid email or password"));
    }
}
