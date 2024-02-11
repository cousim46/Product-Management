package product.management.app.product.dto

import product.management.api.product.dto.request.ProductApiCreate
import product.management.api.product.dto.request.ProductApiUpdate
import product.management.app.product.enums.Size
import product.management.app.product.utils.Barcode
import java.time.LocalDateTime

data class ProductCreate(
    val category: String,
    val price: Long,
    val name: String,
    val content: String,
    val barcode: String,
    val expirationDate: LocalDateTime,
    val size: Size,
    val prefix: String,
    val productIdentifier: String,
    val manufacturerCode: String,
    val cost: Long,
) {
    companion object {
        fun of(productCreate: ProductApiCreate): ProductCreate {
            return ProductCreate(
                category = productCreate.category,
                price = productCreate.price,
                name = productCreate.name,
                barcode = Barcode.create(
                    prefix = productCreate.prefix,
                    manufacturerCode = productCreate.manufacturerCode,
                    productIdentifier = productCreate.productIdentifier
                ),
                content = productCreate.content,
                expirationDate = productCreate.expirationDate,
                size = Size.valueOf(productCreate.size),
                prefix = productCreate.prefix,
                productIdentifier = productCreate.productIdentifier,
                manufacturerCode = productCreate.manufacturerCode,
                cost = productCreate.cost
            )
        }
    }
}

data class ProductUpdate(
    val category: String,
    val price: Long,
    val name: String,
    val content: String,
    val barcode: String,
    val expirationDate: LocalDateTime,
    val size: Size,
    val prefix: String,
    val productIdentifier: String,
    val manufacturerCode: String,
    val cost: Long,
) {
    companion object {
        fun of(productApiUpdate: ProductApiUpdate): ProductUpdate {
            return ProductUpdate(
                category = productApiUpdate.category,
                price = productApiUpdate.price,
                name = productApiUpdate.name,
                barcode = Barcode.create(
                    prefix = productApiUpdate.prefix,
                    manufacturerCode = productApiUpdate.manufacturerCode,
                    productIdentifier = productApiUpdate.productIdentifier
                ),
                content = productApiUpdate.content,
                expirationDate = productApiUpdate.expirationDate,
                size = Size.valueOf(productApiUpdate.size),
                prefix = productApiUpdate.prefix,
                productIdentifier = productApiUpdate.productIdentifier,
                manufacturerCode = productApiUpdate.manufacturerCode,
                cost = productApiUpdate.cost
            )
        }

    }
}
