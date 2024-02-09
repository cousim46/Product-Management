package product.management.app.product

import org.springframework.data.jpa.repository.JpaRepository
import product.management.app.product.domain.Product

interface ProductRepository : JpaRepository<Product, Long> {
}