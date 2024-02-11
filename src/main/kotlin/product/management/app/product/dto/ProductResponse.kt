package product.management.app.product.dto

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.data.domain.Slice
import product.management.app.product.domain.Product
import product.management.app.product.enums.Size
import java.time.LocalDateTime

data class ProductDetailInfo(
    val name: String,
    val price: Long,
    val cost: Long,
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
                size =  product.size,
                cost = product.cost
            )
        }
    }
}

data class Products(
    val isLast: Boolean,
    val isFirst: Boolean,
    val limit: Int,
    val page: Int,
    val infos: List<ProductsInfo>
) {
    companion object  {
        fun of(products: Slice<Product>) : Products {
            return Products(
                isLast =  products.isLast,
                isFirst = products.isFirst,
                page = products.number,
                limit = products.size,
                infos = products.content.map { ProductsInfo(
                    productId = it.id,
                    name = it.name,
                    price = it.price,
                    content = it.content,
                    expirationDate = it.expirationDate,
                    size = it.size,
                    createdAt = it.createdAt
                ) }
            )
        }
    }
}

data class ProductsInfo(
    val productId: Long,
    val name: String,
    val price: Long,
    val content: String,
    @JsonFormat(pattern = "yyyy년 MM월 dd일 HH시 mm분 ss초")
    val expirationDate: LocalDateTime,
    val size: Size,
    @JsonFormat(pattern = "yyyy년 MM월 dd일 HH시 mm분 ss초")
    val createdAt: LocalDateTime
)