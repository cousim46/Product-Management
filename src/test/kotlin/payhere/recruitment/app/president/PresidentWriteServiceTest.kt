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
    @Autowired
    lateinit var bCryptPasswordEncoder: BCryptPasswordEncoder

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
    @Test
    @DisplayName("핸드폰 번호가 11글자 미만이면 예외가 발생한다.")
    fun occurPhoneLengthLessThan11Exception() {
        //given
        val phone = "1".repeat(10)
        val password = "payhere"
        val salt = RandomNumber.create()

        //when
        val errorCode = assertThrows<PayhereException> {
            presidentWriteService.create(phone = phone, password = password, salt = salt)
        }.errorCode

        //then
        Assertions.assertEquals("핸드폰 번호는 11글자여야 합니다.", errorCode.message)
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, errorCode.status)
    }


    @Test
    @DisplayName("핸드폰 번호가 11글자이면 사장님을 생성한다.")
    fun occurPhoneLengthEquals11Create() {
        //given
        val phone = "1".repeat(11)
        val password = "payhere"
        val salt = RandomNumber.create()

        //when
        val savePresident = presidentWriteService.create(phone = phone, password = password, salt = salt)

        //then
        val findPresident = presidentRepository.findById(savePresident.id).get()
        Assertions.assertEquals(savePresident.phone, findPresident.phone)
        Assertions.assertEquals(savePresident.salt, findPresident.salt)
    }

    @Test
    @DisplayName("솔트암호화로 사장님이 입력한 비밀번호와 회원가입된 비밀번호가 일치하지 않는다.")
    fun encodePassword() {
        //given
        val phone = "1".repeat(11)
        val password = "payhere"
        val salt = RandomNumber.create()

        //when
        val savePresident = presidentWriteService.create(phone = phone, password = password, salt = salt)

        //then
        val findPresident = presidentRepository.findById(savePresident.id).get()
        Assertions.assertFalse(bCryptPasswordEncoder.matches(password, findPresident.password))
    }

    @Test
    @DisplayName("솔트암호화로 사장님이 입력한 비밀번호와 salt 합친 값과 회원가입된 비밀번호가 일치한다.")
    fun encodePassword2() {
        //given
        val phone = "1".repeat(11)
        val password = "payhere"
        val salt = RandomNumber.create()
        val saltRawPassword = password + salt

        //when
        val savePresident = presidentWriteService.create(phone = phone, password = password, salt = salt)

        //then
        val findPresident = presidentRepository.findById(savePresident.id).get()
        Assertions.assertTrue(bCryptPasswordEncoder.matches(saltRawPassword, findPresident.password))
    }
}