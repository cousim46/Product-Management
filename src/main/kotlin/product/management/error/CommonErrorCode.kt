package product.management.error

import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.*

enum class CommonErrorCode(
    val status: HttpStatus,
    val message: String,
) {
   VIOLATION_PHONE_LENGTH_CONSTRAINTS(BAD_REQUEST,"핸드폰 번호는 11글자여야 합니다."),
   VIOLATION_PHONE_START_011_LENGTH_CONSTRAINTS(BAD_REQUEST,"핸드폰 번호는 10글자여야 합니다."),
   VIOLATION_PHONE_REGEX_CONSTRAINTS(BAD_REQUEST,"핸드폰 번호가 양식에 맞지않습니다."),
    NOT_MATCH_ID_OR_PASSWORD(BAD_REQUEST,"아이디 또는 비밀번호가 일치하지 않습니다."),

    REFRESH_TOKEN_EXPIRE(UNAUTHORIZED,"리프레쉬 토큰이 만료되었습니다."),
    ACCESS_TOKEN_EXPIRE(UNAUTHORIZED,"액세스 토큰이 만료되었습니다."),
    UNAUTHENTICATED(UNAUTHORIZED, "인증된 사용자가 아닙니다."),

    ACCESS_DENIED(FORBIDDEN, "권한이 없는 접근입니다."),

    NOT_EXSISTS_INFO(NOT_FOUND,"존재하지 않은 정보입니다.");

    fun statusValue(): Int {
        return status.value()
    }
}