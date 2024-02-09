package payhere.recruitment.app.manager.dto.request

import com.fasterxml.jackson.annotation.JsonProperty
import payhere.recruitment.error.CommonErrorCode.*
import payhere.recruitment.error.CommonException
import java.security.SecureRandom
import java.util.regex.Pattern

class RandomNumber {
    companion object {
        fun create(): Int {
            var secureRandom = SecureRandom();
            secureRandom.setSeed(100_000)
            return secureRandom.nextInt(999_999)
        }
    }
}

data class PresidentCreate(
    @JsonProperty("phone")
    val phone: String,
    val password: String,
) {
    companion object {
        const val PHONE_BASIC_CONSTRAINTS_LENGTH = 11
        const val PHONE_START_011_CONSTRAINTS_LENGTH = 10
        const val PHONE_START_011 = "011"

        const val PHONE_BASIC_REGEX = "^010[0-9]{8}\$"
        const val PHONE_START_011_REGEX = "^011\\d{7}\$"

    }
    init {
        val startNumber = phone.substring(0, 3)

        if(startNumber == PHONE_START_011) {
            require(phone.length == PHONE_START_011_CONSTRAINTS_LENGTH) {
                throw CommonException(VIOLATION_PHONE_START_011_LENGTH_CONSTRAINTS)
            }
        }else {
            require(phone.length == PHONE_BASIC_CONSTRAINTS_LENGTH) {
                throw CommonException(VIOLATION_PHONE_LENGTH_CONSTRAINTS)
            }
        }
        require(Pattern.matches(PHONE_START_011_REGEX, phone) || Pattern.matches(PHONE_BASIC_REGEX, phone) ) {
            throw CommonException(VIOLATION_PHONE_REGEX_CONSTRAINTS)
        }
    }
}

data class LoginInfo(
    val id: Long,
)

data class Longin(
    val phone: String,
    val password: String,
)