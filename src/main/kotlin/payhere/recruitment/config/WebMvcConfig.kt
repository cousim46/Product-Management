package payhere.recruitment.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import payhere.recruitment.app.manager.ManagerRepository
import payhere.recruitment.security.LoginInterceptor

@Configuration
class WebMvcConfig(
    private val managerRepository: ManagerRepository,
): WebMvcConfigurer {

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(LoginInterceptor(managerRepository))
    }

}