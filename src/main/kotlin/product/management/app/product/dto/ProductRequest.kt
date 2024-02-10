package product.management.app.product.dto

import product.management.api.product.dto.request.ProductApiCreate
import product.management.app.product.enums.Size
import product.management.app.product.utils.Barcode
import java.time.LocalDateTime

data class ProductInfo(
    val category: String,
    val price: Int,
    val name: String,
    val explain: String,
    val barcode: String,
    val expirationDate: LocalDateTime,
    val size: Size,
    val prefix: String,
    val productIdentifier: String,
    val manufacturerCode: String,
) {

    companion object {
        fun of(productCreate: ProductApiCreate): ProductInfo {
            return ProductInfo(
                category = productCreate.category,
                price = productCreate.price,
                name = productCreate.name,
                barcode = Barcode.create(
                    prefix = productCreate.prefix,
                    manufacturerCode = productCreate.manufacturerCode,
                    productIdentifier = productCreate.productIdentifier
                ),
                explain = productCreate.explain,
                expirationDate = productCreate.expirationDate,
                size = productCreate.size,
                prefix = productCreate.prefix,
                productIdentifier = productCreate.productIdentifier,
                manufacturerCode = productCreate.manufacturerCode
            )
        }
    }
}