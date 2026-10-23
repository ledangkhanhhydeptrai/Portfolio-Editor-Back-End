
package com.example.demo.exception;

import com.example.demo.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ApiResponse<Object>> handleMaxUploadSize(
            MaxUploadSizeExceededException ex,
            HttpServletRequest request
    ) {
        log.error(
                "[UPLOAD SIZE ERROR] URI: {}",
                request.getRequestURI(),
                ex
        );

        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                .body(ApiResponse.builder()
                        .status(413)
                        .message("File quá lớn. Vui lòng giảm dung lượng video.")
                        .data(null)
                        .build());
    }

    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<ApiResponse<Object>> handleMultipart(
            MultipartException ex,
            HttpServletRequest request
    ) {
        log.error(
                "[MULTIPART ERROR] URI: {}",
                request.getRequestURI(),
                ex
        );

        return ResponseEntity.badRequest()
                .body(ApiResponse.builder()
                        .status(400)
                        .message("Không thể đọc dữ liệu upload. Hãy kiểm tra file và dung lượng request.")
                        .data(null)
                        .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidation(
            MethodArgumentNotValidException ex
    ) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors()
                .forEach(error ->
                        errors.put(
                                error.getField(),
                                error.getDefaultMessage()
                        )
                );

        log.warn("[VALIDATION ERROR] {}", errors);

        return handleBadRequest(
                new BadRequestException("Dữ liệu không hợp lệ", errors)
        );
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<Object>> handleBadRequest(
            BadRequestException ex
    ) {
        log.warn("[BAD REQUEST] {}", ex.getMessage());

        return ResponseEntity.badRequest()
                .body(ApiResponse.builder()
                        .status(400)
                        .message(ex.getMessage())
                        .data(ex.getErrors())
                        .build());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse<Object>> handleBadCredentials(
            BadCredentialsException ex
    ) {
        log.warn("[AUTH ERROR] {}", ex.getMessage());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.builder()
                        .status(401)
                        .message("Thông tin đăng nhập không hợp lệ")
                        .data(null)
                        .build());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Object>> handleAccessDenied(
            AccessDeniedException ex
    ) {
        log.warn("[ACCESS DENIED] {}", ex.getMessage());

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ApiResponse.builder()
                        .status(403)
                        .message("Bạn không có quyền thực hiện thao tác này")
                        .data(null)
                        .build());
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Object>> handleBusiness(
            BusinessException ex
    ) {
        log.warn("[BUSINESS ERROR] {}", ex.getMessage());

        return ResponseEntity.status(ex.getStatus())
                .body(ApiResponse.builder()
                        .status(ex.getStatus().value())
                        .message(ex.getMessage())
                        .data(null)
                        .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleAll(
            Exception ex,
            HttpServletRequest request
    ) {
        log.error(
                "[INTERNAL SERVER ERROR] URI: {}",
                request.getRequestURI(),
                ex
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.builder()
                        .status(500)
                        .message("Có lỗi xảy ra trên server. Vui lòng kiểm tra server logs.")
                        .data(null)
                        .build());
    }
}
