package payhere.recruitment.app.token

import org.springframework.data.jpa.repository.JpaRepository
import payhere.recruitment.app.token.domain.RefreshToken

interface RefreshTokenRepository : JpaRepository<RefreshToken, Long> {
    fun findByPresidentId(presidentId: Long): RefreshToken?

    fun existsByPresidentId(presidentId: Long): Boolean
    fun deleteByPresidentId(presidentId: Long): Boolean
}