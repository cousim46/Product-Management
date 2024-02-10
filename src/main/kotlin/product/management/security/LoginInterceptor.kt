package product.management.security

import org.springframework.core.MethodParameter
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import product.management.app.manager.ManagerRepository
import product.management.api.manager.dto.request.LoginInfo
import product.management.error.CommonErrorCode
import product.management.error.CommonException
import product.management.security.annotation.LoginUser

class LoginInterceptor(
    private val managerRepository: ManagerRepository
) : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.hasParameterAnnotation(LoginUser::class.java)
    }

    override fun resolveArgument(parameter: MethodParameter, mavContainer: ModelAndViewContainer?, webRequest: NativeWebRequest, binderFactory: WebDataBinderFactory?): Any? {
        val authentication = SecurityContextHolder.getContext().authentication
        val manager = managerRepository.findByPhone(authentication.name)
            ?: throw CommonException(CommonErrorCode.ACCESS_TOKEN_EXPIRE)
        return LoginInfo(manager.id)
    }
}