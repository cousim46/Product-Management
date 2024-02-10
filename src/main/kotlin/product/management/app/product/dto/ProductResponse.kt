package product.management.app.product.dto

import com.fasterxml.jackson.annotation.JsonFormat
import product.management.app.product.domain.Product
import product.management.app.product.enums.Size
import java.time.LocalDateTime

data class ProductDetailInfo(
    val name: String,
    val price: Int,
    val content: String,
    @JsonFormat(pattern = "yyyy년 MM월 dd일 HH시 mm분 ss초")
    val expirationDate: LocalDateTime,
    val size: Size,
) {
    companion object {
        fun toResponse(product: Product): ProductDetailInfo {
            return ProductDetailInfo(
                name =  product.name,
                price =  product.price,
                content =  product.content,
                expirationDate = product.expirationDate,
                size =  product.size
            )
        }
    }
}