package product.management.app.product

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import product.management.api.product.dto.request.ProductApiCreate
import product.management.api.product.dto.request.ProductApiUpdate
import product.management.app.manager.ManagerRepository
import product.management.app.product.domain.CompanyInfo
import product.management.app.product.dto.ProductCreate
import product.management.app.product.dto.ProductUpdate
import product.management.app.product.enums.Size
import product.management.app.product.utils.Barcode
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
                code = productInfo.code,
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

    @Test
    @DisplayName("상품을 등록하려는 사장님이 존재하지 않으면 예외가 발생한다.")
    fun occurNotExistsManagerInfoException() {
        val productInfo = createProductInfo()
        val managerId: Long = -1

        //when
        val errorCode = assertThrows<CommonException> {
            productWriteService.create(managerId, productInfo)
        }.errorCode

        //then
        assertEquals("존재하지 않은 정보입니다.", errorCode.message)
        assertEquals(HttpStatus.NOT_FOUND, errorCode.status)
    }

    @Test
    @DisplayName("사장님이 상품정보를 입력하면 상품이 생성된다.")
    fun createPresidentInputProductInfo() {
        //given
        val manager = managerRepository.create()
        val productInfo = createProductInfo()
        //when
        val savedProductId =
            productWriteService.create(managerId = manager.id, productCreate = productInfo)

        //then
        val findProduct = productRepository.findById(savedProductId).get()
        assertEquals(productInfo.barcode, findProduct.barcode)
        assertEquals(manager.id, findProduct.returnManagerId())
        assertEquals(productInfo.name, findProduct.name)
        assertEquals(productInfo.category, findProduct.category)
        assertEquals(productInfo.price, findProduct.price)
        assertEquals(productInfo.content, findProduct.content)
        assertEquals(productInfo.content, findProduct.content)
        assertEquals(productInfo.size, findProduct.size)

    }

    @Test
    @DisplayName("존재하지 않는 상품 사이즈에 대한 상품을 등록할 경우 예외가 발생한다.")
    fun occurNotExistsSizeException() {
        val size: String = "M"

        //when
        val errorCode = assertThrows<CommonException> {
            createProductInfo(size = size)
        }.errorCode

        //then
        assertEquals("잘못된 상품 사이즈 입니다.", errorCode.message)
        assertEquals(HttpStatus.BAD_REQUEST, errorCode.status)
    }

    @Test
    @DisplayName("상품을 삭제하려는 사장님이 존재하지 않으면 예외가 발생한다.")
    fun occurDeleteProductInfoNotExistsManagerInfoException() {
        //given
        val managerId = -1L
        val manager2 = managerRepository.create()
        val product = productRepository.create(manager = manager2)

        //when
        val errorCode = assertThrows<CommonException> {
            productWriteService.delete(managerId = managerId, productId = product.id)
        }.errorCode

        //then
        assertEquals("존재하지 않은 정보입니다.", errorCode.message)
        assertEquals(HttpStatus.NOT_FOUND, errorCode.status)
    }


    @Test
    @DisplayName("상품을 삭제하려는 사장님이 등록한 상품이 아닌 경우 예외가 발생한다.")
    fun occurDeleteProductInfoNotEqualsManagerAndProduct() {
        //given
        val manager1 = managerRepository.create()
        val manager2 = managerRepository.create()
        val product = productRepository.create(manager = manager2)

        //when
        val errorCode = assertThrows<CommonException> {
            productWriteService.delete(managerId = manager1.id, productId = product.id)
        }.errorCode

        //then
        assertEquals("존재하지 않는 상품정보입니다.", errorCode.message)
        assertEquals(HttpStatus.NOT_FOUND, errorCode.status)
    }

    @Test
    @DisplayName("삭제하려는 상품이 사장님이 등록한 상품일 경우 상품을 삭제한다.")
    fun deleteProduct() {
        //given
        val manager = managerRepository.create()
        val product = productRepository.create(manager = manager)
        val productId = product.id

        //when
        productWriteService.delete(managerId = manager.id, productId = productId)

        //then
        val findByIdOrNull = productRepository.findByIdOrNull(productId)
        assertEquals(null, findByIdOrNull)
    }

    @Test
    @DisplayName("상품을 수정하려는 사장님이 존재하지 않으면 예외가 발생한다.")
    fun occurUpdateProductInfoNotExistsManagerInfoException() {
        //given
        val managerId = -1L
        val manager2 = managerRepository.create()
        val product = productRepository.create(manager = manager2)

        //when
        val errorCode = assertThrows<CommonException> {
            productWriteService.update(
                managerId = managerId,
                productId = product.id,
                updateProductInfo()
            )
        }.errorCode

        //then
        assertEquals("존재하지 않은 정보입니다.", errorCode.message)
        assertEquals(HttpStatus.NOT_FOUND, errorCode.status)
    }


    @Test
    @DisplayName("수정하려는 상품이 사장님이 등록한 상품이 아닌 경우 예외가 발생한다.")
    fun occurUpdateProductInfoNotEqualsManagerAndProduct() {
        //given
        val manager1 = managerRepository.create()
        val manager2 = managerRepository.create()
        val product = productRepository.create(manager = manager2)

        //when
        val errorCode = assertThrows<CommonException> {
            productWriteService.update(
                managerId = manager1.id,
                productId = product.id,
                updateProductInfo()
            )
        }.errorCode

        //then
        assertEquals("존재하지 않는 상품정보입니다.", errorCode.message)
        assertEquals(HttpStatus.NOT_FOUND, errorCode.status)
    }

    @Test
    @DisplayName("수정하려는 상품의 바코드도 같이 변경 될 경우 해당 바코드가 이미 등록된 바코드일 경우 예외가 발생한다.")
    fun occurUpdateProductInfoAlreadyExistsBarcode() {
        //given
        val manager1 = managerRepository.create()
        val product = productRepository.create(manager = manager1)
        val companyInfo = CompanyInfo(
            code = "123",
            productIdentifier = "test",
            manufacturerCode = "123"
        )
        productRepository.create(
            manager = manager1,
            companyInfo = companyInfo,
            barcode = Barcode.create(
                productIdentifier = companyInfo.productIdentifier,
                code = companyInfo.code,
                manufacturerCode = companyInfo.manufacturerCode
            )
        )

        //when
        val errorCode = assertThrows<CommonException> {
            productWriteService.update(
                managerId = manager1.id,
                productId = product.id,
                updateProductInfo(
                    productIdentifier = companyInfo.productIdentifier,
                    code = companyInfo.code,
                    manufacturerCode = companyInfo.manufacturerCode
                )
            )
        }.errorCode

        //then
        assertEquals("이미 존재하는 바코드입니다.", errorCode.message)
        assertEquals(HttpStatus.CONFLICT, errorCode.status)
    }

    @Test
    @DisplayName("사장님은 상품을 수정할 수 있다.")
    fun updateManagerProductInfo() {
        //given
        val manager = managerRepository.create()
        val product = productRepository.create(manager = manager)
        val changeBeforeProductName = product.name
        val changeBeforeProductprice = product.price

        //when
        productWriteService.update(
            managerId = manager.id, productId = product.id,
            productUpdate = updateProductInfo(name = "바닐라라떼", price = 6000)
        )

        //then
        val findProduct = productRepository.findById(product.id).get()
        assertNotEquals(changeBeforeProductName, findProduct.name)
        assertNotEquals(changeBeforeProductprice, findProduct.price)
        assertEquals("바닐라라떼",findProduct.name)
        assertEquals(6000,findProduct.price)
    }


    private fun createProductInfo(
        category: String = "음료",
        price: Long = 5000,
        name: String = "아메리카노",
        explain: String = "커피입니다.",
        expirationDate: LocalDateTime = LocalDateTime.now(),
        code: String = "880",
        productIdentifier: String = "ICM",
        manufacturerCode: String = "1234",
        size: String = Size.SMALL.name,
        cost: Long = 1000
    ): ProductCreate {
        return ProductCreate.of(
            ProductApiCreate(
                category = category,
                price = price,
                name = name,
                content = explain,
                expirationDate = expirationDate,
                size = size,
                code = code,
                productIdentifier = productIdentifier,
                manufacturerCode = manufacturerCode,
                cost = cost,
            )
        )
    }

    private fun updateProductInfo(
        category: String = "음료",
        price: Long = 5000,
        name: String = "아메리카노",
        explain: String = "커피입니다.",
        expirationDate: LocalDateTime = LocalDateTime.now(),
        code: String = "880",
        productIdentifier: String = "ICM",
        manufacturerCode: String = "1234",
        size: String = Size.SMALL.name,
        cost: Long = 1000
    ): ProductUpdate {
        return ProductUpdate.of(
            ProductApiUpdate(
                category = category,
                price = price,
                name = name,
                content = explain,
                expirationDate = expirationDate,
                size = size,
                code = code,
                productIdentifier = productIdentifier,
                manufacturerCode = manufacturerCode,
                cost = cost,
            )
        )
    }
}