package product.management.api.product

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import product.demo.app.common.CommonResponse
import product.management.api.manager.dto.request.LoginInfo
import product.management.api.product.dto.request.ProductApiCreate
import product.management.app.product.ProductWriteService
import product.management.app.product.dto.ProductInfo
import product.management.security.annotation.LoginUser

@RestController
@RequestMapping("/api/product")
class ProductController(
    private val productWriteService: ProductWriteService,
) {

    @PostMapping
    fun create(@LoginUser loginInfo: LoginInfo, @RequestBody productApiCreate: ProductApiCreate): CommonResponse {
        productWriteService.create(id = loginInfo.id, productInfo = ProductInfo.of(productApiCreate))
        return CommonResponse.toResponse()
    }
}