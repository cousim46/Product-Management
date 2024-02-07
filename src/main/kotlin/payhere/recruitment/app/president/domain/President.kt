package payhere.recruitment.app.president.domain

import payhere.demo.error.PayhereErrorCode.VIOLATION_PHONE_CONSTRAINTS
import payhere.demo.error.PayhereException
import payhere.recruitment.app.common.BaseEntity
import javax.persistence.Column
import javax.persistence.Entity

@Entity
class President(
    @Column(nullable = false)
    var phone: String,
    @Column(nullable = false)
    var password: String,
    @Column(nullable = false)
    var salt: String,
) : BaseEntity() {
    companion object {
        const val PHONE_CONSTRAINTS_LENGTH = 11
    }
    init {
        require(phone.length == PHONE_CONSTRAINTS_LENGTH) {
            throw PayhereException(VIOLATION_PHONE_CONSTRAINTS)
        }
    }
}