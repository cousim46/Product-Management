package product.management.app.product.domain

import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
class CompanyInfo(
    @Column(nullable = false)
    val code: String,
    @Column(nullable = false)
    val productIdentifier: String,
    @Column(nullable = false)
    val manufacturerCode: String,
)