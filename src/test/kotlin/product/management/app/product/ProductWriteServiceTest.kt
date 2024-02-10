package product.management.app.product

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import product.management.api.product.dto.request.ProductApiCreate
import product.management.app.manager.ManagerRepository
import product.management.app.product.domain.CompanyInfo
import product.management.app.product.dto.ProductInfo
import product.management.app.product.enums.Size
import product.management.create
import product.management.error.CommonException
import java.time.LocalDateTime

@SpringBootTest
class ProductWriteServiceTest(
    @Autowired
    private val productWriteService: ProductWriteService,
    @Autowired
    private val productRepository: ProductRepository,
    @Autowired
    private val managerRepository: ManagerRepository,
) {


    @AfterEach
    fun init() {
        productRepository.deleteAll()
        managerRepository.deleteAll()
    }

    @Test
    @DisplayName("이미 존재하는 바코드가 있을경우 예외가 발생한다.")
    fun occurAlreadyExistsBarcodeException() {
        //given
        val manager = managerRepository.create()
        val productInfo = createProductInfo()
         productRepository.create(
            manager = manager, companyInfo = CompanyInfo(
                code = productInfo.prefix,
                productIdentifier = productInfo.productIdentifier,
                manufacturerCode = productInfo.manufacturerCode
            ), barcode = productInfo.barcode
        )

        //when
        val errorCode = assertThrows<CommonException> {
            productWriteService.create(manager.id, productInfo)
        }.errorCode

        //then
        assertEquals("이미 존재하는 바코드입니다.", errorCode.message)
        assertEquals(HttpStatus.CONFLICT, errorCode.status)
    }


    private fun createProductInfo(
        category: String = "음료",
        price: Int = 5000,
        name: String = "아메리카노",
        explain: String = "커피입니다.",
        expirationDate: LocalDateTime = LocalDateTime.now(),
        prefix: String = "880",
        productIdentifier: String = "ICM",
        manufacturerCode: String = "1234",
        size: Size = Size.SMALL,
    ): ProductInfo {
        return ProductInfo.of(
            ProductApiCreate(
                category = category,
                price = price,
                name = name,
                explain = explain,
                expirationDate = expirationDate,
                size = size,
                prefix = prefix,
                productIdentifier = productIdentifier,
                manufacturerCode = manufacturerCode,
            )
        )
    }
}