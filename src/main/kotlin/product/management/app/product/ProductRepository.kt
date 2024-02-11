package product.management.app.product

import org.springframework.data.jpa.repository.JpaRepository
import product.management.app.product.domain.Product

interface ProductRepository : JpaRepository<Product, Long>, ProductQueryDslRepository {
    fun existsByBarcode(barcode: String) : Boolean

    fun findByIdAndManagerId(productId: Long, managerId: Long): Product?

    fun deleteByIdAndManagerId(productId: Long, managerId: Long)
}