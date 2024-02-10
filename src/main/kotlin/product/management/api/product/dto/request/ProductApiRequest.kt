package product.management.api.product.dto.request

import com.fasterxml.jackson.annotation.JsonFormat
import product.management.app.product.enums.Size
import product.management.error.CommonErrorCode.NOT_EXSISTS_PRODUCT_SIZE
import product.management.error.CommonException
import java.time.LocalDateTime

data class ProductApiCreate(
    val category: String,
    val price: Int,
    val name: String,
    val content: String,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val expirationDate: LocalDateTime,
    val size: String,
    val prefix: String,
    val productIdentifier: String,
    val manufacturerCode: String,
) {
    init {
        require(Size.contains(size)) {
            throw CommonException(NOT_EXSISTS_PRODUCT_SIZE)
        }
    }
}