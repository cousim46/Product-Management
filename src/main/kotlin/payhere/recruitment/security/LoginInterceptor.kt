package payhere.recruitment.security

import org.springframework.core.MethodParameter
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import payhere.recruitment.app.manager.ManagerRepository
import payhere.recruitment.app.manager.dto.request.LoginInfo
import payhere.recruitment.error.CommonErrorCode
import payhere.recruitment.error.CommonException
import payhere.recruitment.security.annotation.LoginUser

class LoginInterceptor(
    private val managerRepository: ManagerRepository
) : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.hasParameterAnnotation(LoginUser::class.java)
    }

    override fun resolveArgument(parameter: MethodParameter, mavContainer: ModelAndViewContainer?, webRequest: NativeWebRequest, binderFactory: WebDataBinderFactory?): Any? {
        val authentication = SecurityContextHolder.getContext().authentication
        val president = managerRepository.findByPhone(authentication.name)
            ?: throw CommonException(CommonErrorCode.ACCESS_TOKEN_EXPIRE)
        return LoginInfo(president.id)
    }
}