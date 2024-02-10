package product.management.app.product.domain

import product.management.app.common.domain.BaseEntity
import product.management.app.manager.domain.Manager
import product.management.app.product.enums.Size
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Product(
    @Column(nullable = false)
    val category: String,
    @Column(nullable = false)
    val price: Int,
    @Column(nullable = false)
    var name: String,
    @Column(nullable = false)
    val content: String,
    @Column(nullable = false)
    val expirationDate: LocalDateTime,
    @Column(nullable = false, unique = true)
    val barcode: String,
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val size: Size,
    @Embedded
    val companyInfo: CompanyInfo,
    var namePrefix: String,
    @ManyToOne(fetch = FetchType.LAZY)
    val manager: Manager,
) : BaseEntity() {

    fun returnManagerId() = manager.id
}