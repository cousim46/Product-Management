package product.management.app.token

import org.springframework.data.jpa.repository.JpaRepository
import product.management.app.token.domain.RefreshToken

interface RefreshTokenRepository : JpaRepository<RefreshToken, Long> {
    fun findByManagerId(managerId: Long): RefreshToken?

    fun existsByManagerId(managerId: Long): Boolean
    fun deleteByManagerId(managerId: Long)
}