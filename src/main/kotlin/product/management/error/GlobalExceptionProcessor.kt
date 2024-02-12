package product.management.error

import com.fasterxml.jackson.databind.exc.InvalidFormatException
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import product.demo.app.common.CommonResponse

@RestControllerAdvice
class GlobalExceptionProcessor {

    @ExceptionHandler(CommonException::class)
    fun commonExceptionResponse(
        commonException: CommonException
    ): ResponseEntity<CommonResponse> {
        return CommonExceptionResponse.toResponse(errorCode = commonException.errorCode)
    }

    @ExceptionHandler(MissingKotlinParameterException::class)
    fun notNullExceptionResponse(e: MissingKotlinParameterException): ResponseEntity<CommonResponse> {
        return CommonExceptionResponse.toNotNullableResponse("${e.path[0].fieldName} 필수로 입력해야합니다.")
    }
    @ExceptionHandler(InvalidFormatException::class)
    fun invalidFormatExceptionResponse(e: InvalidFormatException) : ResponseEntity<CommonResponse> {
        return CommonExceptionResponse.invalidFormatResponse("${e.path[0].fieldName} 타입이 알맞지 않습니다.")
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun methodArgumentTypeMismatchExceptionResponse2(e: MethodArgumentTypeMismatchException) : ResponseEntity<CommonResponse> {
        return CommonExceptionResponse.methodArgumentTypeMismatchResponse("${e.parameter.parameterName} 타입이 알맞지 않습니다.")
    }
}