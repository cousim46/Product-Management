package product.management.app.manager.domain


import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.http.HttpStatus
import product.management.error.CommonException
import product.management.api.manager.dto.request.RandomNumber

class ManagerTest {

    @Test
    @DisplayName("핸드폰 번호가 11글자를 초과하면 예외가 발생한다.")
    fun occur010PhoneLengthExceed11Exception() {
        //given
        val phone = "010123412341"
        val password = "password"
        val salt = RandomNumber.create()

        //when
        val errorCode = assertThrows<CommonException> {
            Manager(
                phone = phone,
                password = password,
                salt = salt
            )
        }.errorCode

        //then
        assertEquals("010으로 시작하는 핸드폰 번호는 11글자여야 합니다.",errorCode.message)
        assertEquals(HttpStatus.BAD_REQUEST,errorCode.status)
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
            Manager(
                phone = phone,
                password = password,
                salt = salt
            )
        }.errorCode

        //then
        assertEquals("010으로 시작하는 핸드폰 번호는 11글자여야 합니다.",errorCode.message)
        assertEquals(HttpStatus.BAD_REQUEST,errorCode.status)
    }

    @Test
    @DisplayName("핸드폰 번호 앞자리가 011이 아니고 핸드폰 번호가 11글자이면 사장님을 생성한다.")
    fun occur010PhoneLengthEquals11Create() {
        //given
        val phone = "01012341234"
        val password = "password"
        val salt = RandomNumber.create()

        //when
        val manager = Manager(phone = phone, password = password, salt = salt)

        //then
        assertEquals(phone, manager.phone)
        assertEquals(password, manager.password)
        assertEquals(salt, manager.salt)
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
            Manager(
                phone = phone,
                password = password,
                salt = salt
            )
        }.errorCode

        //then
        assertEquals("011로 시작하는 핸드폰 번호는 10글자여야 합니다.",errorCode.message)
        assertEquals(HttpStatus.BAD_REQUEST,errorCode.status)
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
            Manager(
                phone = phone,
                password = password,
                salt = salt
            )
        }.errorCode

        //then
        assertEquals("011로 시작하는 핸드폰 번호는 10글자여야 합니다.",errorCode.message)
        assertEquals(HttpStatus.BAD_REQUEST,errorCode.status)
    }


    @Test
    @DisplayName("핸드폰 번호가 011로 시작할 경우 핸드폰 번호가 10글자이면 사장님을 생성한다.")
    fun occur011PhoneLengthEquals11Create() {
        //given
        val phone = "0111234123"
        val password = "password"
        val salt = RandomNumber.create()

        //when
        val manager = Manager(phone = phone, password = password, salt = salt)

        //then
        assertEquals(phone, manager.phone)
        assertEquals(password, manager.password)
        assertEquals(salt, manager.salt)
    }
}