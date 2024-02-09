package product.management.error

class CommonException(
    val errorCode: CommonErrorCode,
) : RuntimeException()