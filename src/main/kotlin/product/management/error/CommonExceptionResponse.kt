package product.management.error

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import product.demo.app.common.CommonResponse
import product.demo.app.common.Meta

class CommonExceptionResponse {

    companion object {
        fun toResponse(errorCode: CommonErrorCode): ResponseEntity<CommonResponse> {
            return ResponseEntity.status(errorCode.status)
                .body(
                    CommonResponse.toResponse(
                        Meta.of(
                            code = errorCode.statusValue(),
                            message = errorCode.message,
                        )
                    )
                )
        }

        fun toNotNullableResponse(errorMessage: String): ResponseEntity<CommonResponse> {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                    CommonResponse.toResponse(
                        Meta.of(
                            code = HttpStatus.BAD_REQUEST.value(),
                            message = errorMessage,
                        )
                    )
                )
        }
        fun invalidFormatResponse(errorMessage: String) : ResponseEntity<CommonResponse> {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                    CommonResponse.toResponse(
                        Meta.of(
                            code = HttpStatus.BAD_REQUEST.value(),
                            message = errorMessage,
                        )
                    )
                )
        }
        fun methodArgumentTypeMismatchResponse(errorMessage: String) : ResponseEntity<CommonResponse> {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                    CommonResponse.toResponse(
                        Meta.of(
                            code = HttpStatus.BAD_REQUEST.value(),
                            message = errorMessage,
                        )
                    )
                )
        }
    }
}