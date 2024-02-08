package payhere.recruitment.app.president.domain

import payhere.recruitment.error.PayhereErrorCode.VIOLATION_PHONE_LENGTH_CONSTRAINTS
import payhere.recruitment.error.PayhereException
import payhere.recruitment.app.common.domain.BaseEntity
import payhere.recruitment.error.PayhereErrorCode
import payhere.recruitment.error.PayhereErrorCode.VIOLATION_PHONE_START_011_LENGTH_CONSTRAINTS
import javax.persistence.Column
import javax.persistence.Entity

@Entity
class President(
    @Column(nullable = false)
    var phone: String,
    @Column(nullable = false)
    var password: String,
    @Column(nullable = false)
    var salt: Int,
) : BaseEntity() {
    companion object {
        const val PHONE_CONSTRAINTS_LENGTH = 11
        const val PHONE_START_011_CONSTRAINTS_LENGTH = 10
        const val PHONE_START_011 = "011"
    }
    init {
        val startNumber = phone.substring(0, 3)
        if(startNumber == PHONE_START_011) {
            require(phone.length == PHONE_START_011_CONSTRAINTS_LENGTH) {
                throw PayhereException(VIOLATION_PHONE_START_011_LENGTH_CONSTRAINTS)
            }
        }
        require(phone.length == PHONE_CONSTRAINTS_LENGTH) {
            throw PayhereException(VIOLATION_PHONE_LENGTH_CONSTRAINTS)
        }
    }
}