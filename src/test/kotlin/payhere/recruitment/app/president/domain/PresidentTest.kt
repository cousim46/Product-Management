package payhere.recruitment.app.president.domain


import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.http.HttpStatus
import payhere.demo.error.PayhereException

class PresidentTest {

    @Test
    @DisplayName("핸드폰 번호가 11글자를 초과하면 예외가 발생한다.")
    fun occurPhoneLengthExceed11Exception() {
        //given
        val phone = "1".repeat(12)
        val password = "password"
        val salt = "salt"

        //when
        val errorCode = assertThrows<PayhereException> {
            President(
                phone = phone,
                password = password,
                salt = salt
            )
        }.errorCode

        //then
        Assertions.assertEquals("핸드폰 번호는 11글자여야 합니다.",errorCode.message)
        Assertions.assertEquals(HttpStatus.BAD_REQUEST,errorCode.status)
    }
}