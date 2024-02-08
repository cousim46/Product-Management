package payhere.recruitment.app.president.domain

import payhere.recruitment.error.PayhereErrorCode.VIOLATION_PHONE_LENGTH_CONSTRAINTS
import payhere.recruitment.error.PayhereException
import payhere.recruitment.app.common.domain.BaseEntity
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
    }
    init {
        require(phone.length == PHONE_CONSTRAINTS_LENGTH) {
            throw PayhereException(VIOLATION_PHONE_LENGTH_CONSTRAINTS)
        }
    }
}