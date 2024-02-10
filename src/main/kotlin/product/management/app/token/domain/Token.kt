package product.management.app.token.domain

import product.management.app.common.domain.BaseEntity
import product.management.app.manager.domain.Manager
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.OneToOne

@Entity
class Token(
    @Column(nullable = false)
    val refresh: String,
    val access: String,
    @OneToOne
    val manager: Manager,
    val expireAt: LocalDateTime,
) : BaseEntity() {
}