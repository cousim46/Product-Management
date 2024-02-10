package product.management.app.product.utils

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class LanguageSeparationTest {

    @Test
    @DisplayName("상품이름을 초성으로 분리한다.")
    fun test() {
        //given
        val productName = "아이스아메리카노"

        //when
        val extractPrefix = LanguageSeparation.extractPrefix(productName)

        //then
        Assertions.assertEquals(extractPrefix, "ㅇㅇㅅㅇㅁㄹㅋㄴ")
    }
}