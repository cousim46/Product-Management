package payhere.recruitment.app.president.domain


import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.http.HttpStatus
import payhere.recruitment.error.PayhereException
import payhere.recruitment.app.president.dto.request.RandomNumber

class PresidentTest {

    @Test
    @DisplayName("핸드폰 번호가 11글자를 초과하면 예외가 발생한다.")
    fun occurPhoneLengthExceed11Exception() {
        //given
        val phone = "1".repeat(12)
        val password = "password"
        val salt = RandomNumber.create()

        //when
        val errorCode = assertThrows<PayhereException> {
            President(
                phone = phone,
                password = password,
                salt = salt
            )
        }.errorCode

        //then
        assertEquals("핸드폰 번호는 11글자여야 합니다.",errorCode.message)
        assertEquals(HttpStatus.BAD_REQUEST,errorCode.status)
    }

    @Test
    @DisplayName("핸드폰 번호 앞자리가 011이 아닐경우 핸드폰 번호가 11글자 미만이면 예외가 발생한다.")
    fun occurPhoneLengthLessThan11Exception() {
        //given
        val phone = "0101234123"
        val password = "password"
        val salt = RandomNumber.create()

        //when
        val errorCode = assertThrows<PayhereException> {
            President(
                phone = phone,
                password = password,
                salt = salt
            )
        }.errorCode

        //then
        assertEquals("핸드폰 번호는 11글자여야 합니다.",errorCode.message)
        assertEquals(HttpStatus.BAD_REQUEST,errorCode.status)
    }


    @Test
    @DisplayName("핸드폰 번호 앞자리가 011이 아니고 핸드폰 번호가 11글자이면 사장님을 생성한다.")
    fun occurPhoneLengthEquals11Create() {
        //given
        val phone = "01012341234"
        val password = "password"
        val salt = RandomNumber.create()

        //when
        val president = President(phone = phone, password = password, salt = salt)

        //then
        assertEquals(phone, president.phone)
        assertEquals(password, president.password)
        assertEquals(salt, president.salt)
    }
}