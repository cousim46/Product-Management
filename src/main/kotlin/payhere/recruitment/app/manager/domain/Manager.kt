package payhere.recruitment.app.manager.domain

import payhere.recruitment.error.CommonErrorCode.VIOLATION_PHONE_LENGTH_CONSTRAINTS
import payhere.recruitment.error.CommonException
import payhere.recruitment.app.common.domain.BaseEntity
import payhere.recruitment.app.manager.enums.Position
import payhere.recruitment.error.CommonErrorCode.VIOLATION_PHONE_START_011_LENGTH_CONSTRAINTS
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Entity
class Manager(
    @Column(nullable = false)
    var phone: String,
    @Column(nullable = false)
    var password: String,
    @Column(nullable = false)
    var salt: Int,
    @Enumerated(EnumType.STRING)
    val position: Position
) : BaseEntity() {
    companion object {
        const val PHONE_CONSTRAINTS_LENGTH = 11
        const val PHONE_START_011_CONSTRAINTS_LENGTH = 10
        const val PHONE_START_011 = "011"
    }

    init {
        val startNumber = phone.substring(0, 3)
        if (startNumber == PHONE_START_011) {
            require(phone.length == PHONE_START_011_CONSTRAINTS_LENGTH) {
                throw CommonException(VIOLATION_PHONE_START_011_LENGTH_CONSTRAINTS)
            }
        } else {
            require(phone.length == PHONE_CONSTRAINTS_LENGTH) {
                throw CommonException(VIOLATION_PHONE_LENGTH_CONSTRAINTS)
            }
        }
    }
}