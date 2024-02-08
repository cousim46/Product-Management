package payhere.recruitment.error

import org.springframework.http.HttpStatus

enum class PayhereErrorCode(
    val status: HttpStatus,
    val message: String,
) {
   VIOLATION_PHONE_LENGTH_CONSTRAINTS(HttpStatus.BAD_REQUEST,"핸드폰 번호는 11글자여야 합니다."),
   VIOLATION_PHONE_START_011_LENGTH_CONSTRAINTS(HttpStatus.BAD_REQUEST,"핸드폰 번호는 10글자여야 합니다."),
   VIOLATION_PHONE_REGEX_CONSTRAINTS(HttpStatus.BAD_REQUEST,"핸드폰 번호가 양식에 맞지않습니다.");
    fun statusValue(): Int {
        return status.value()
    }
}