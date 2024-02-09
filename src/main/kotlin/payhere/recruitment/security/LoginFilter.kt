package payhere.recruitment.security

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import payhere.recruitment.security.annotation.TokenAuthenticationEntryPoint
import payhere.recruitment.token.TokenProvider
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class LoginFilter(
    private val tokenProvider: TokenProvider,
    private val tokenAuthenticationEntryPoint: TokenAuthenticationEntryPoint
) : OncePerRequestFilter() {
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        tokenProvider.resolveToken(request = request as? HttpServletRequest?)?.also {
            val loginToken = it.split(" ")[1]
            if(!it.toLowerCase().startsWith("bearer ") || !tokenProvider.validateToken(loginToken)) {
                tokenAuthenticationEntryPoint.commence(request,response = response, null)
                return
            }
            val authentication = tokenProvider.getAuthentication(loginToken)
            SecurityContextHolder.getContext().authentication = authentication
        }
        filterChain.doFilter(request, response)
    }

}
