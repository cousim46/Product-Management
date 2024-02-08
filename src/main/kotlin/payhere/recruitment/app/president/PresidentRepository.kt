package payhere.recruitment.app.president

import org.springframework.data.jpa.repository.JpaRepository
import payhere.recruitment.app.president.domain.President

interface PresidentRepository : JpaRepository<President, Long> {

    fun findByPhone(phone: String): President?
}