package product.management.app.manager

import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import product.management.error.CommonException
import product.management.api.manager.dto.request.RandomNumber

@SpringBootTest
class ManagerWriteServiceTest(
    @Autowired
    private val managerWriteService: ManagerWriteService,
    @Autowired
    private val managerRepository: ManagerRepository,
    @Autowired
    private val bCryptPasswordEncoder: BCryptPasswordEncoder,
) {

    @BeforeEach
    fun deleteAll() {
        managerRepository.deleteAll()
    }

    @Test
    @DisplayName("핸드폰 번호가 11글자를 초과하면 예외가 발생한다.")
    fun occur010PhoneLengthExceed11Exception() {
        //given
        val phone = "010123412341"
        val password = "password"
        val salt = RandomNumber.create()

        //when
        val errorCode = assertThrows<CommonException> {
            managerWriteService.create(phone = phone, password = password, salt = salt)
        }.errorCode

        //then
        Assertions.assertEquals("핸드폰 번호는 11글자여야 합니다.", errorCode.message)
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, errorCode.status)
    }

    @Test
    @DisplayName("핸드폰 번호 앞자리가 011이 아닐경우 핸드폰 번호가 11글자 미만이면 예외가 발생한다.")
    fun occur010PhoneLengthLessThan11Exception() {
        //given
        val phone = "0101234123"
        val password = "password"
        val salt = RandomNumber.create()

        //when
        val errorCode = assertThrows<CommonException> {
            managerWriteService.create(phone = phone, password = password, salt = salt)
        }.errorCode

        //then
        Assertions.assertEquals("핸드폰 번호는 11글자여야 합니다.", errorCode.message)
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, errorCode.status)
    }

    @Test
    @DisplayName("핸드폰 번호 앞자리가 011이 아니고 핸드폰 번호가 11글자이면 사장님을 생성한다.")
    fun occur010PhoneLengthEquals11Create() {
        //given
        val phone = "01012341234"
        val password = "password"
        val salt = RandomNumber.create()

        //when
        val president =
            managerWriteService.create(phone = phone, password = password, salt = salt)

        //then
        Assertions.assertEquals(phone, president.phone)
        Assertions.assertEquals(salt, president.salt)
    }

    @Test
    @DisplayName("핸드폰 번호가 011로 시작할 경우 핸드폰 번호가 10글자를 초과하면 예외가 발생한다.")
    fun occur011PhoneLengthExceed11Exception() {
        //given
        val phone = "01112341234"
        val password = "password"
        val salt = RandomNumber.create()

        //when
        val errorCode = assertThrows<CommonException> {
            managerWriteService.create(phone = phone, password = password, salt = salt)
        }.errorCode

        //then
        Assertions.assertEquals("핸드폰 번호는 10글자여야 합니다.", errorCode.message)
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, errorCode.status)
    }

    @Test
    @DisplayName("핸드폰 번호가 011로 시작할 경우 핸드폰 번호가 10글자 미만이면 예외가 발생한다.")
    fun occur011PhoneLengthLessThan11Exception() {
        //given
        val phone = "011123412"
        val password = "password"
        val salt = RandomNumber.create()

        //when
        val errorCode = assertThrows<CommonException> {
            managerWriteService.create(phone = phone, password = password, salt = salt)
        }.errorCode

        //then
        Assertions.assertEquals("핸드폰 번호는 10글자여야 합니다.", errorCode.message)
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, errorCode.status)
    }


    @Test
    @DisplayName("핸드폰 번호가 011로 시작할 경우 핸드폰 번호가 10글자이면 사장님을 생성한다.")
    fun occur011PhoneLengthEquals11Create() {
        //given
        val phone = "0111234123"
        val password = "password"
        val salt = RandomNumber.create()

        //when
        val president = managerWriteService.create(phone = phone, password = password, salt = salt)

        //then
        Assertions.assertEquals(phone, president.phone)
        Assertions.assertEquals(salt, president.salt)
    }


    @Test
    @DisplayName("솔트암호화로 사장님이 입력한 비밀번호와 회원가입된 비밀번호가 일치하지 않는다.")
    fun encodePasswordNotEqualInputPassword() {
        //given
        val phone = "01012341234"
        val password = "product"
        val salt = RandomNumber.create()

        //when
        val savePresident =
            managerWriteService.create(phone = phone, password = password, salt = salt)

        //then
        val findPresident = managerRepository.findById(savePresident.id).get()
        Assertions.assertFalse(bCryptPasswordEncoder.matches(password, findPresident.password))
    }

    @Test
    @DisplayName("솔트암호화로 사장님이 입력한 비밀번호와 salt 합친 값과 회원가입된 비밀번호가 일치한다.")
    fun encodePasswordEqualInputPassword() {
        //given
        val phone = "01012341234"
        val password = "product"
        val salt = RandomNumber.create()
        val saltRawPassword = password + salt

        //when
        val savePresident =
            managerWriteService.create(phone = phone, password = password, salt = salt)

        //then
        val findPresident = managerRepository.findById(savePresident.id).get()
        Assertions.assertTrue(
            bCryptPasswordEncoder.matches(
                saltRawPassword,
                findPresident.password
            )
        )
    }
}