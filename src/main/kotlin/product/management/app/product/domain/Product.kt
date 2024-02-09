package product.management.app.product.domain

import product.management.app.common.domain.BaseEntity
import product.management.app.product.enums.Size
import java.time.LocalDateTime
import javax.persistence.Entity

@Entity
class Product(
    val category: String,
    val price: Int,
    val name: String,
    val explain: String,
    val expirationDate: LocalDateTime,
    val barcode: String,
    val size: Size
) : BaseEntity() {
}