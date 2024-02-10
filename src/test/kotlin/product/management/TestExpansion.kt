package product.management

import product.management.app.manager.ManagerRepository
import product.management.app.manager.domain.Manager
import product.management.app.product.ProductRepository
import product.management.app.product.domain.CompanyInfo
import product.management.app.product.domain.Product
import product.management.app.product.enums.Size
import product.management.app.product.utils.LanguageSeparation
import java.time.LocalDateTime


internal fun ManagerRepository.create(
    phone: String = "01012341234",
    password: String = "test",
    salt: Int = 1234,
): Manager = this.saveAndFlush(
    Manager(
        phone = phone,
        password = password,
        salt = salt,
    ),
)

internal fun ProductRepository.create(
    category: String = "음료",
    price: Int = 5000,
    name: String = "아메리카노",
    explain: String = "커피입니다.",
    expirationDate: LocalDateTime = LocalDateTime.now(),
    barcode: String,
    size: Size = Size.SMALL,
    companyInfo: CompanyInfo,
    manager: Manager,
): Product = this.saveAndFlush(
    Product(
        category = category,
        price = price,
        name = name,
        content = explain,
        expirationDate = expirationDate,
        barcode = barcode,
        size = size,
        companyInfo = companyInfo,
        manager= manager,
        namePrefix = LanguageSeparation.extractPrefix(name)
        ),
)