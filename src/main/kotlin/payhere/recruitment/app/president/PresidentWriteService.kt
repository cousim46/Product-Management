package payhere.recruitment.app.president

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import payhere.recruitment.app.president.domain.President

@Transactional
@Service
class PresidentWriteService(
    private val presidentRepository: PresidentRepository,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder,
) {
    fun create(phone: String, password: String, salt: Int): President {
        val encodePassword = encodePassword(salt = salt, password = password)
        val president = President(phone = phone, password = encodePassword, salt = salt)
        return presidentRepository.save(president)
    }

    private fun encodePassword(salt: Int, password: String): String {
        return bCryptPasswordEncoder.encode(password + salt)
    }
}