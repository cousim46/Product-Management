package product.management.app.product

import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import product.management.app.manager.ManagerRepository
import product.management.app.product.domain.CompanyInfo
import product.management.app.product.domain.Product
import product.management.app.product.dto.ProductCreate
import product.management.app.product.dto.ProductUpdate
import product.management.app.product.utils.LanguageSeparation
import product.management.error.CommonErrorCode
import product.management.error.CommonErrorCode.ALREADY_EXSISTS_BARCODE
import product.management.error.CommonException

@Transactional
@Service
class ProductWriteService(
    private val productRepository: ProductRepository,
    private val managerRepository: ManagerRepository,
) {
    fun create(managerId: Long, productCreate: ProductCreate): Long {
        val manager = managerRepository.findByIdOrNull(managerId)
            ?: throw CommonException(CommonErrorCode.NOT_EXSISTS_INFO)

        val companyInfo = createCompanyInfo(
            code = productCreate.code, manufacturer = productCreate.manufacturerCode,
            productIdentifier = productCreate.productIdentifier
        )
        try {
            val product = productRepository.save(
            Product(
                category = productCreate.category,
                price = productCreate.price,
                name = productCreate.name,
                content = productCreate.content,
                expirationDate = productCreate.expirationDate,
                size = productCreate.size,
                companyInfo = companyInfo,
                manager = manager,
                barcode = productCreate.barcode,
                namePrefix = LanguageSeparation.extractPrefix(productCreate.name),
                cost = productCreate.cost
            )
        )
            return product.id
        } catch (e : DataIntegrityViolationException) {
            throw CommonException(ALREADY_EXSISTS_BARCODE)
        }
    }

    fun delete(productId: Long, managerId: Long) {
        val manager = managerRepository.findByIdOrNull(managerId) ?: throw CommonException(
            CommonErrorCode.NOT_EXSISTS_INFO
        )
        val product =
            productRepository.findByIdAndManagerId(productId = productId, managerId = manager.id)
                ?: throw CommonException(
                    CommonErrorCode.NOT_EXSISTS_PRODUCT_INFO
                )
        productRepository.deleteByIdAndManagerId(productId = product.id, managerId = manager.id)
    }

    fun update(managerId: Long, productId: Long, productUpdate: ProductUpdate) {
        val manager = managerRepository.findByIdOrNull(managerId)
            ?: throw CommonException(CommonErrorCode.NOT_EXSISTS_INFO)
        val product =
            productRepository.findByIdAndManagerId(productId = productId, managerId = manager.id)
                ?: throw CommonException(
                    CommonErrorCode.NOT_EXSISTS_PRODUCT_INFO
                )
       if (productUpdate.barcode != product.barcode) {
            if (productRepository.existsByBarcode(productUpdate.barcode)) {
                throw CommonException(ALREADY_EXSISTS_BARCODE)
            }
        }
        val companyInfo = createCompanyInfo(
            code = productUpdate.code, manufacturer = productUpdate.manufacturerCode,
            productIdentifier = productUpdate.productIdentifier
        )
            product.update(
                category = productUpdate.category,
                price = productUpdate.price,
                name = productUpdate.name,
                content = productUpdate.content,
                expirationDate = productUpdate.expirationDate,
                size = productUpdate.size,
                companyInfo = companyInfo,
                barcode = productUpdate.barcode,
                namePrefix = LanguageSeparation.extractPrefix(productUpdate.name),
                cost = productUpdate.cost
            )

    }

    private fun createCompanyInfo(
        code: String,
        manufacturer: String,
        productIdentifier: String
    ): CompanyInfo {
        return CompanyInfo(
            code = code,
            manufacturerCode = manufacturer,
            productIdentifier = productIdentifier
        )
    }

}