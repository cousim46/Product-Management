package payhere.recruitment.app.token

import org.springframework.data.jpa.repository.JpaRepository
import payhere.recruitment.app.token.domain.RefreshToken

interface RefreshTokenRepository : JpaRepository<RefreshToken, Long> {
    fun findByManagerId(managerId: Long): RefreshToken?

    fun existsByManagerId(managerId: Long): Boolean
    fun deleteByManagerId(managerId: Long)
}