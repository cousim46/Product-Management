package product.management.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import product.management.app.manager.ManagerRepository
import product.management.security.LoginInterceptor

@Configuration
class WebMvcConfig(
    private val managerRepository: ManagerRepository,
): WebMvcConfigurer {

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(LoginInterceptor(managerRepository))
    }

}