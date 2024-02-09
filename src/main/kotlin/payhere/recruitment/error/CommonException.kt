package payhere.recruitment.error

class CommonException(
    val errorCode: CommonErrorCode,
) : RuntimeException()