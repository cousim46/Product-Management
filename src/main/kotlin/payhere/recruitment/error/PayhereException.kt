package payhere.demo.error

class PayhereException(
    val errorCode: PayhereErrorCode,
) : RuntimeException() {
}