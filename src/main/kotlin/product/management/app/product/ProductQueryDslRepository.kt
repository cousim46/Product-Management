package product.management.app.product

import org.springframework.data.domain.Slice
import product.management.app.product.domain.Product

interface ProductQueryDslRepository {
    fun findProduct(keyword: String?,managerId: Long, page: Int, limit: Int, offset: Long): Slice<Product>
}