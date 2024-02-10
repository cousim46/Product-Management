package product.management.security

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import product.management.app.token.RefreshTokenRepository
import product.management.security.annotation.TokenAuthenticationEntryPoint
import product.management.token.TokenProvider
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class LoginFilter(
    private val tokenProvider: TokenProvider,
    private val tokenAuthenticationEntryPoint: TokenAuthenticationEntryPoint,
    private val refreshTokenRepository: RefreshTokenRepository,
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        tokenProvider.resolveToken(request = request as? HttpServletRequest?)?.also {
            val loginToken = it.split(" ")
            if (loginToken.size != 2 || !it.toLowerCase()
                    .startsWith("bearer ") || !tokenProvider.validateToken(loginToken[1]) ||
                !refreshTokenRepository.existsByAccessToken(accessToken = loginToken[1])
                ) {
                tokenAuthenticationEntryPoint.commence(request, response = response, null)
                return
            }
            val authentication = tokenProvider.getAuthentication(loginToken[1])
            SecurityContextHolder.getContext().authentication = authentication
        }
        filterChain.doFilter(request, response)
    }

}
