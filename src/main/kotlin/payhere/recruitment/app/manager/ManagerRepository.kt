package payhere.recruitment.app.manager

import org.springframework.data.jpa.repository.JpaRepository
import payhere.recruitment.app.manager.domain.Manager

interface ManagerRepository : JpaRepository<Manager, Long> {

    fun findByPhone(phone: String): Manager?
}