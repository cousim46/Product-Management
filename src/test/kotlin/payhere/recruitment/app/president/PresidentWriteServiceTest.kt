package payhere.recruitment.app.president

import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import payhere.recruitment.error.PayhereException
import payhere.recruitment.app.president.dto.request.RandomNumber

@SpringBootTest
class PresidentWriteServiceTest(
) {
    @Autowired
    lateinit var presidentWriteService: PresidentWriteService
    @Autowired
    lateinit var presidentRepository: PresidentRepository

    @AfterEach
    fun deleteAll() {
        presidentRepository.deleteAll()
    }

    @Test
    @DisplayName("핸드폰 번호가 11글자 이상이면 예외 발생한다.")
    fun occurPhoneLengthExceed11Exception() {
        //given
        val phone = "1".repeat(12)
        val salt = RandomNumber.create()
        val password = "payhere"

        //when
        val errorCode = assertThrows<PayhereException> {
            presidentWriteService.create(phone = phone, password = password, salt = salt)
        }.errorCode

        //then
        Assertions.assertEquals("핸드폰 번호는 11글자여야 합니다.", errorCode.message)
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, errorCode.status)
    }
}