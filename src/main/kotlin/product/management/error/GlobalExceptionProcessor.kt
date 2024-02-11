package product.management.error

import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import product.demo.app.common.CommonResponse

@RestControllerAdvice
class GlobalExceptionProcessor {

    @ExceptionHandler(CommonException::class)
    fun commonExceptionResponse(
        commonException: CommonException
    ): ResponseEntity<CommonResponse> {
        return CommonExceptionResponse.toResponse(errorCode = commonException.errorCode)
    }
}