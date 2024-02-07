package payhere.demo.error

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import payhere.demo.app.common.CommonResponse
import payhere.recruitment.error.PayhereExceptionResponse

@RestControllerAdvice
class GlobalExceptionProcessor {

    @ExceptionHandler(PayhereException::class)
    fun payhereExceptionResponse(
        payhereException: PayhereException
    ): ResponseEntity<CommonResponse> {
        return PayhereExceptionResponse.toResponse(errorCode = payhereException.errorCode)
    }
}