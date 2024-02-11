package product.management.app.product.domain

import product.management.app.common.domain.BaseEntity
import product.management.app.manager.domain.Manager
import product.management.app.product.enums.Size
import java.time.LocalDateTime
import javax.persistence.*
import kotlin.math.cos

@Entity
class Product(
    @Column(nullable = false)
    var category: String,
    @Column(nullable = false)
    var price: Long,
    @Column(nullable = false)
    var name: String,
    @Column(nullable = false)
    var content: String,
    @Column(nullable = false)
    var expirationDate: LocalDateTime,
    @Column(nullable = false, unique = true)
    var barcode: String,
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var size: Size,
    @Embedded
    var companyInfo: CompanyInfo,
    var namePrefix: String,
    @ManyToOne(fetch = FetchType.LAZY)
    val manager: Manager,
    @Column(nullable = false)
    var cost: Long,
) : BaseEntity() {

    fun returnManagerId() = manager.id

    fun update(category: String, price: Long,
               name: String, content: String,
               barcode: String, expirationDate: LocalDateTime,
               size: Size, companyInfo: CompanyInfo,
               cost: Long, namePrefix: String) {
        this.category = category
        this.price = price
        this.name = name
        this.content = content
        this.barcode =barcode
        this.expirationDate = expirationDate
        this.size = size
        this.companyInfo = companyInfo
        this.cost = cost
        this.namePrefix = namePrefix
    }
}