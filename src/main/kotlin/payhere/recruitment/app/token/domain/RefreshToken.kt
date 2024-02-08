package payhere.recruitment.app.token.domain

import payhere.recruitment.app.common.domain.BaseEntity
import payhere.recruitment.app.president.domain.President
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.OneToOne

@Entity
class RefreshToken(
    @Column(nullable = false)
    val token: String,
    @OneToOne
    val president: President,
    val expireAt: LocalDateTime,
) : BaseEntity() {
}