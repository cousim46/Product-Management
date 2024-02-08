package payhere.recruitment.error

class PayhereException(
    val errorCode: PayhereErrorCode,
) : RuntimeException()