package payhere.demo.error

import org.springframework.http.HttpStatus

enum class PayhereErrorCode(
    val status: HttpStatus,
    val message: String,
) {
   VIOLATION_PHONE_CONSTRAINTS(HttpStatus.BAD_REQUEST,"핸드폰 번호는 11글자여야 합니다.");
    fun statusValue(): Int {
        return status.value()
    }
}