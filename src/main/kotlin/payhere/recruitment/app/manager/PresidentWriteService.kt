package payhere.recruitment.app.manager

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import payhere.recruitment.app.manager.domain.Manager
import payhere.recruitment.app.token.RefreshTokenRepository
import payhere.recruitment.error.CommonErrorCode
import payhere.recruitment.error.CommonException
import payhere.recruitment.token.LoginToken
import payhere.recruitment.token.TokenProvider
import java.util.*

@Transactional
@Service
class PresidentWriteService(
    private val managerRepository: ManagerRepository,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder,
    private val tokenProvider: TokenProvider,
) {
    fun create(phone: String, password: String, salt: Int): Manager {
        val encodePassword = encodePassword(salt = salt, password = password)
        val manager = Manager(phone = phone, password = encodePassword, salt = salt)
        return managerRepository.save(manager)
    }

    fun login(phone: String, password: String, now: Date): LoginToken {
        val president = managerRepository.findByPhone(phone)
            ?: throw CommonException(CommonErrorCode.NOT_MATCH_ID_OR_PASSWORD)
        val saltPassword = password + president.salt
        require(bCryptPasswordEncoder.matches(saltPassword, president.password)) {
            throw CommonException(CommonErrorCode.NOT_MATCH_ID_OR_PASSWORD)
        }
        if (refreshTokenRepository.existsByManagerId(managerId = president.id)) {
            refreshTokenRepository.deleteByManagerId(managerId = president.id)
        }
        return tokenProvider.getToken(id = president.id, now = now, role = president.position.name)
    }

    private fun encodePassword(salt: Int, password: String): String {
        return bCryptPasswordEncoder.encode(password + salt)
    }
}