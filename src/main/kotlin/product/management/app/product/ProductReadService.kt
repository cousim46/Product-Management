package product.management.app.product

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import product.management.app.manager.ManagerRepository
import product.management.app.product.domain.Product
import product.management.error.CommonErrorCode
import product.management.error.CommonException

@Transactional(readOnly = false)
@Service
class ProductReadService(
    private val productRepository: ProductRepository,
    private val managerRepository: ManagerRepository,
) {

    fun getDetail(productId: Long, managerId: Long): Product {
        val manager = managerRepository.findByIdOrNull(managerId) ?: throw CommonException(
            CommonErrorCode.NOT_EXSISTS_INFO
        )
        val product =
            productRepository.findByIdAndManagerId(productId = productId, managerId = manager.id)
                ?: throw CommonException(
                    CommonErrorCode.NOT_EXSISTS_PRODUCT_INFO
                )
        return product
    }
}