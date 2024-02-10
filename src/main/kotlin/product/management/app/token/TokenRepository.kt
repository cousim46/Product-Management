package product.management.app.token

import org.springframework.data.jpa.repository.JpaRepository
import product.management.app.token.domain.Token

interface TokenRepository : JpaRepository<Token, Long> {
    fun findByManagerId(managerId: Long): Token?

    fun existsByManagerId(managerId: Long): Boolean
    fun deleteByManagerId(managerId: Long)

    fun existsByAccess(access: String) : Boolean
}