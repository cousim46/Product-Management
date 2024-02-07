package payhere.demo.error

import org.springframework.http.HttpStatus

enum class PayhereErrorCode(
    val status: HttpStatus,
    val message: String,
) {
   ;
    fun statusValue(): Int {
        return status.value()
    }
}