package product.management.app.product

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import product.management.app.manager.ManagerRepository
import product.management.app.product.domain.CompanyInfo
import product.management.app.product.domain.Product
import product.management.app.product.dto.ProductInfo
import product.management.error.CommonErrorCode
import product.management.error.CommonErrorCode.ALREADY_EXSISTS_BARCODE
import product.management.error.CommonException

@Transactional
@Service
class ProductWriteService(
    private val productRepository: ProductRepository,
    private val managerRepository: ManagerRepository,
) {
    fun create(id: Long, productInfo: ProductInfo) : Long {
        val manager = managerRepository.findByIdOrNull(id)
            ?: throw CommonException(CommonErrorCode.NOT_EXSISTS_INFO)

        require(!productRepository.existsByBarcode(productInfo.barcode)) {
            throw CommonException(ALREADY_EXSISTS_BARCODE)
        }
        val companyInfo = CompanyInfo(
            code = productInfo.prefix,
            manufacturerCode = productInfo.manufacturerCode,
            productIdentifier = productInfo.productIdentifier
        )

        val product = productRepository.save(
            Product(
                category = productInfo.category,
                price = productInfo.price,
                name = productInfo.name,
                content = productInfo.content,
                expirationDate = productInfo.expirationDate,
                size = productInfo.size,
                companyInfo = companyInfo,
                manager = manager,
                barcode = productInfo.barcode
            )
        )
        return product.id
    }
}