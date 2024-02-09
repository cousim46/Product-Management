package payhere.recruitment.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import payhere.recruitment.security.LoginFilter
import payhere.recruitment.security.TokenAccessDeniedHandler
import payhere.recruitment.security.annotation.TokenAuthenticationEntryPoint
import payhere.recruitment.token.TokenProvider

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val tokenProvider: TokenProvider,
    private val tokenAccessDeniedHandler: TokenAccessDeniedHandler,
    private val tokenAuthenticationEntryPoint: TokenAuthenticationEntryPoint,
) : WebSecurityConfigurerAdapter() {

    @Bean
    fun bCryptPasswordEncoder() : BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    override fun configure(http: HttpSecurity) {
        http.httpBasic().disable()
            .cors().disable()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .addFilterBefore(LoginFilter(tokenProvider,tokenAuthenticationEntryPoint), UsernamePasswordAuthenticationFilter::class.java)
            .exceptionHandling()
            .authenticationEntryPoint(tokenAuthenticationEntryPoint)
            .accessDeniedHandler(tokenAccessDeniedHandler)
            .and()
            .authorizeRequests()
            .antMatchers("/**")
            .permitAll()
            .anyRequest().authenticated()
    }
}