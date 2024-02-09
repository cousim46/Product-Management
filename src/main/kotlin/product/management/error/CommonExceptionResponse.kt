package product.management.error

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
    }
}