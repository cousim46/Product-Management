package payhere.recruitment.error

import org.springframework.http.ResponseEntity
import payhere.demo.app.common.CommonResponse
import payhere.demo.app.common.Meta

class PayhereExceptionResponse {

    companion object {
        fun toResponse(errorCode: PayhereErrorCode): ResponseEntity<CommonResponse> {
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