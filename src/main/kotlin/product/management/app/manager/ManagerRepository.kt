package product.management.app.manager

import org.springframework.data.jpa.repository.JpaRepository
import product.management.app.manager.domain.Manager

interface ManagerRepository : JpaRepository<Manager, Long> {

    fun findByPhone(phone: String): Manager?
}