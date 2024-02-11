package product.management.app.product

import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Slice
import org.springframework.data.domain.SliceImpl
import org.springframework.stereotype.Repository
import product.management.app.manager.domain.QManager.manager
import product.management.app.product.domain.Product
import product.management.app.product.domain.QProduct.product

@Repository
class ProductQueryDslRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory,
) : ProductQueryDslRepository {

    override fun findProduct(
        keyword: String?,
        managerId: Long,
        page: Int,
        limit: Int,
        offset: Long
    ): Slice<Product> {
        val result = jpaQueryFactory.selectFrom(product)
            .join(product.manager, manager)
            .where(containsKeyword(keyword), eqManagerId(managerId))
            .limit(limit + 1L)
            .offset(offset)
            .orderBy(product.createdAt.desc()).fetch()


        var hasNext = false
        if (result.size > limit) {
            result.removeAt(limit)
            hasNext = true
        }
        return SliceImpl(result, PageRequest.of(page, limit), hasNext)
    }

    fun containsKeyword(keyword: String?): BooleanExpression? {
        var booleanExpression: BooleanExpression? = null
        if (keyword != null && keyword.isNotBlank()) {
            for (word in keyword) {
                if(word == ' ') {
                    continue
                }
                if (booleanExpression == null) {
                    booleanExpression = product.name.contains(word.toString())
                        .or(product.namePrefix.contains(word.toString()))
                } else {
                    booleanExpression = booleanExpression.and(
                        product.name.contains(word.toString())
                            .or(product.namePrefix.contains(word.toString()))
                    )
                }
            }
        }
        return booleanExpression
    }

    fun eqManagerId(id: Long): BooleanExpression {
        return product.manager.id.eq(id)
    }
}