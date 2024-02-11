package product.management.api.product

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import product.demo.app.common.CommonResponse
import product.demo.app.common.Data
import product.management.api.manager.dto.request.LoginInfo
import product.management.api.product.dto.request.ProductApiCreate
import product.management.app.product.ProductReadService
import product.management.app.product.ProductWriteService
import product.management.app.product.dto.ProductDetailInfo
import product.management.app.product.dto.ProductInfo
import product.management.security.annotation.LoginUser

@RestController
@RequestMapping("/api/product")
class ProductController(
    private val productWriteService: ProductWriteService,
    private val productReadService: ProductReadService,
) {

    @PostMapping
    fun create(@LoginUser loginInfo: LoginInfo, @RequestBody productApiCreate: ProductApiCreate): CommonResponse {
        productWriteService.create(id = loginInfo.id, productInfo = ProductInfo.of(productApiCreate))
        return CommonResponse.toResponse()
    }

    @GetMapping("/{product-id}")
    fun getDetail(@LoginUser loginInfo: LoginInfo, @PathVariable("product-id") productId: Long): CommonResponse {
        val product = productReadService.getDetail(productId = productId, managerId = loginInfo.id)
        val data = Data.of(ProductDetailInfo.toResponse(product))
        return CommonResponse.toResponse(data = data)
    }

    @DeleteMapping("/{product-id}")
    fun delete(@LoginUser loginInfo: LoginInfo, @PathVariable("product-id") productId: Long): CommonResponse {
        productWriteService.delete(productId = productId, managerId = loginInfo.id)
        return CommonResponse.toResponse()
    }
    @PutMapping("/{product-id}")
    fun update(@LoginUser loginInfo: LoginInfo, @PathVariable("product-id") productId: Long): CommonResponse {

        return CommonResponse.toResponse()
    }
}