package product.management.app.manager

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import product.management.app.manager.domain.Manager
import product.management.app.token.TokenRepository
import product.management.error.CommonErrorCode
import product.management.error.CommonException
import product.management.token.LoginToken
import product.management.token.TokenProvider
import java.util.*

@Transactional
@Service
class ManagerWriteService(
    private val managerRepository: ManagerRepository,
    private val tokenRepository: TokenRepository,
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
        if (tokenRepository.existsByManagerId(managerId = president.id)) {
            tokenRepository.deleteByManagerId(managerId = president.id)
        }
        return tokenProvider.getToken(id = president.id, now = now, role = president.position.name)
    }

    fun logout(id: Long) {
        tokenRepository.deleteByManagerId(id)
    }

    private fun encodePassword(salt: Int, password: String): String {
        return bCryptPasswordEncoder.encode(password + salt)
    }
}