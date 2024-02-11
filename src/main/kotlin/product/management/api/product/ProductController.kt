package product.management.api.product

import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*
import product.demo.app.common.CommonResponse
import product.demo.app.common.Data
import product.management.api.manager.dto.request.LoginInfo
import product.management.api.product.dto.request.ProductApiCreate
import product.management.api.product.dto.request.ProductApiUpdate
import product.management.api.product.dto.request.Search
import product.management.app.product.ProductReadService
import product.management.app.product.ProductWriteService
import product.management.app.product.dto.ProductDetailInfo
import product.management.app.product.dto.ProductCreate
import product.management.app.product.dto.ProductUpdate
import product.management.app.product.dto.Products
import product.management.security.annotation.LoginUser

@RestController
@RequestMapping("/api/product")
class ProductController(
    private val productWriteService: ProductWriteService,
    private val productReadService: ProductReadService,
) {

    @PostMapping
    fun create(
        @LoginUser loginInfo: LoginInfo,
        @RequestBody productApiCreate: ProductApiCreate
    ): CommonResponse {
        productWriteService.create(
            managerId = loginInfo.id,
            productCreate = ProductCreate.of(productApiCreate)
        )
        return CommonResponse.toResponse()
    }

    @GetMapping("/{product-id}")
    fun getDetail(
        @LoginUser loginInfo: LoginInfo,
        @PathVariable("product-id") productId: Long
    ): CommonResponse {
        val product = productReadService.getDetail(productId = productId, managerId = loginInfo.id)
        val data = Data.of(ProductDetailInfo.toResponse(product))
        return CommonResponse.toResponse(data = data)
    }

    @DeleteMapping("/{product-id}")
    fun delete(
        @LoginUser loginInfo: LoginInfo,
        @PathVariable("product-id") productId: Long
    ): CommonResponse {
        productWriteService.delete(productId = productId, managerId = loginInfo.id)
        return CommonResponse.toResponse()
    }

    @GetMapping
    fun getProducts(
        @LoginUser loginInfo: LoginInfo,
        search: Search,
        @PageableDefault(page = 0, size = 10) pageable: Pageable
    ): CommonResponse {
        val products = productReadService.getProducts(
            loginInfo.id,
            search.keyword,
            page = pageable.pageNumber,
            limit = pageable.pageSize,
            offset = pageable.offset
        )
        val data = Data.of(Products.of(products))
        return CommonResponse.toResponse(data = data)
    }

    @PutMapping("/{product-id}")
    fun update(
        @LoginUser loginInfo: LoginInfo,
        @PathVariable("product-id") productId: Long,
        @RequestBody productApiUpdateInfo: ProductApiUpdate
    ): CommonResponse {
        productWriteService.update(
            managerId = loginInfo.id,
            productId = productId,
            ProductUpdate.of(productApiUpdateInfo)
        )
        return CommonResponse.toResponse()
    }

}