package product.management.api.product.dto.request

import com.fasterxml.jackson.annotation.JsonFormat
import product.management.app.product.enums.Size
import product.management.error.CommonErrorCode.BAD_REQUEST_PRODUCT_SIZE
import product.management.error.CommonException
import java.time.LocalDateTime

data class ProductApiCreate(
    val category: String,
    val price: Long,
    val name: String,
    val content: String,
    @JsonFormat(pattern = "yyyy년 MM월 dd일 HH시 mm분 ss초")
    val expirationDate: LocalDateTime,
    val size: String,
    val code: String,
    val productIdentifier: String,
    val manufacturerCode: String,
    val cost: Long
) {
    init {
        require(Size.contains(size)) {
            throw CommonException(BAD_REQUEST_PRODUCT_SIZE)
        }
    }
}

data class Search(
    val keyword: String?
)

data class ProductApiUpdate(
    val category: String,
    val price: Long,
    val name: String,
    val content: String,
    @JsonFormat(pattern = "yyyy년 MM월 dd일 HH시 mm분 ss초")
    val expirationDate: LocalDateTime,
    val size: String,
    val code: String,
    val productIdentifier: String,
    val manufacturerCode: String,
    val cost: Long
) {
    init {
        require(Size.contains(size)) {
            throw CommonException(BAD_REQUEST_PRODUCT_SIZE)
        }
    }
}
