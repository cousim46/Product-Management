package payhere.recruitment.app.president.dto.request

import com.fasterxml.jackson.annotation.JsonProperty
import payhere.recruitment.error.PayhereErrorCode.VIOLATION_PHONE_LENGTH_CONSTRAINTS
import payhere.recruitment.error.PayhereErrorCode.VIOLATION_PHONE_REGEX_CONSTRAINTS
import payhere.recruitment.error.PayhereException
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
        const val PHONE_CONSTRAINTS_LENGTH = 11
        const val PHONE_REGEX = "^010[0-9]{8}\$"
    }
    init {
        require(phone.length == PHONE_CONSTRAINTS_LENGTH) {
            throw PayhereException(VIOLATION_PHONE_LENGTH_CONSTRAINTS)
        }
        require(Pattern.matches(PHONE_REGEX, phone)) {
            throw PayhereException(VIOLATION_PHONE_REGEX_CONSTRAINTS)
        }
    }
}