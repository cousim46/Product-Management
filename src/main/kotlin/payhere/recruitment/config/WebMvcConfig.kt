package payhere.recruitment.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import payhere.recruitment.app.president.PresidentRepository
import payhere.recruitment.security.LoginInterceptor

@Configuration
class WebMvcConfig(
    private val presidentRepository: PresidentRepository,
): WebMvcConfigurer {

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(LoginInterceptor(presidentRepository))
    }

}