package payhere.recruitment.app.token.domain

import payhere.recruitment.app.common.domain.BaseEntity
import payhere.recruitment.app.manager.domain.Manager
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.OneToOne

@Entity
class RefreshToken(
    @Column(nullable = false)
    val token: String,
    @OneToOne
    val manager: Manager,
    val expireAt: LocalDateTime,
) : BaseEntity() {
}