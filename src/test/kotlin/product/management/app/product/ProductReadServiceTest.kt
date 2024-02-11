package product.management.app.product

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.Slice
import org.springframework.http.HttpStatus
import product.management.app.manager.ManagerRepository
import product.management.app.product.domain.Product
import product.management.create
import product.management.error.CommonException

@SpringBootTest
class ProductReadServiceTest(
    @Autowired
    private val productReadService: ProductReadService,
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
    @DisplayName("상품을 상세조회 하려는 사장님이 존재하지 않으면 예외가 발생한다.")
    fun occurSelectProductInfoNotExistsManagerInfoException() {
        //given
        val managerId = -1L
        val manager2 = managerRepository.create()
        val product = productRepository.create(manager = manager2)

        //when
        val errorCode = assertThrows<CommonException> {
            productReadService.getDetail(managerId = managerId, productId = product.id)
        }.errorCode

        //then
        assertEquals("존재하지 않은 정보입니다.", errorCode.message)
        assertEquals(HttpStatus.NOT_FOUND, errorCode.status)
    }


    @Test
    @DisplayName("상품을 상세조회 하려는 사장님이 등록한 상품이 아닌 경우 예외가 발생한다.")
    fun occurSelectProductInfoNotEqualsManagerAndProduct() {
        //given
        val manager1 = managerRepository.create()
        val manager2 = managerRepository.create()
        val product = productRepository.create(manager = manager2)

        //when
        val errorCode = assertThrows<CommonException> {
            productReadService.getDetail(managerId = manager1.id, productId = product.id)
        }.errorCode

        //then
        assertEquals("존재하지 않는 상품정보입니다.", errorCode.message)
        assertEquals(HttpStatus.NOT_FOUND, errorCode.status)
    }

    @Test
    @DisplayName("상품을 상세조회 하려는 사장님이 등록한 상품일 경우 상품정보를 확인할 수 있다.")
    fun getDetail() {
        //given
        val manager = managerRepository.create()
        val product = productRepository.create(manager = manager)

        //when
        val findProduct =
            productReadService.getDetail(managerId = manager.id, productId = product.id)

        //then
        assertEquals(findProduct.id, product.id)
        assertEquals(findProduct.name, product.name)
        assertEquals(findProduct.price, product.price)
        assertEquals(findProduct.content, product.content)
        assertEquals(findProduct.barcode, product.barcode)
        assertEquals(findProduct.size, product.size)
        assertEquals(findProduct.expirationDate, product.expirationDate)
    }

    @Test
    @DisplayName("상품 목록을 조회하려는 사장님이 존재하지 않으면 예외가 발생한다.")
    fun occurSelectProductsNotExistsManagerInfoException() {
        //given
        val managerId = -1L
        val keyword = null
        val page = 0
        val limit = 10
        val offset = 0L

        //when
        val errorCode = assertThrows<CommonException> {
            productReadService.getProducts(
                id = managerId,
                keyword = keyword,
                page = page,
                limit = limit,
                offset = offset,
            )
        }.errorCode

        //then
        assertEquals("존재하지 않은 정보입니다.", errorCode.message)
        assertEquals(HttpStatus.NOT_FOUND, errorCode.status)
    }

    @Test
    @DisplayName("사장님이 등록한 상품들을 조회할 수 있다.")
    fun selectProduct() {
        //given
        val manager = managerRepository.create()
        val product1 = productRepository.create(manager = manager, name = "바닐라 라떼")
        val product2 =
            productRepository.create(manager = manager, barcode = "12345123", name = "아이스 아메리카노")
        val keyword = null
        val page = 0
        val limit = 10
        val offset = 0L

        //when
        val products: Slice<Product> = productReadService.getProducts(
            id = manager.id,
            keyword = keyword,
            page = page,
            limit = limit,
            offset = offset,
        )
        println("productReadService = ${products.content}")

        //then
        assertEquals(2,products.content.size )
        assertEquals(products.content[1].name, product1.name)
        assertEquals(products.content[0].name, product2.name)
        assertEquals(true, products.content[0].createdAt.isAfter(products.content[1].createdAt))
    }
}

