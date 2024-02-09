package payhere.recruitment.token


import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SecurityException
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import payhere.recruitment.app.president.PresidentRepository
import payhere.recruitment.app.token.RefreshTokenRepository
import payhere.recruitment.app.token.domain.RefreshToken
import payhere.recruitment.error.PayhereErrorCode.NOT_EXSISTS_INFO
import payhere.recruitment.error.PayhereErrorCode.REFRESH_TOKEN_EXPIRE
import payhere.recruitment.error.PayhereException
import payhere.recruitment.security.LoginUserDetail
import java.security.Key
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit
import java.util.*
import javax.servlet.http.HttpServletRequest

@Component
class TokenProvider(
    private val presidentRepository: PresidentRepository,
    private val refreshTokenRepository: RefreshTokenRepository,
    @Value("\${jwt.secret-key}")
    private val secretKey: String,
    @Value("\${jwt.access-token-expire-time}")
    private val ACCESS_TOKEN_EXPIRE_TIME: String,
    @Value("\${jwt.refresh-token-expire-time}")
    private val REFRESH_TOKEN_EXPIRE_TIME: String,
) {

    val key: Key by lazy {
        Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey))
    }

    fun getToken(id: Long, now: Date): LoginToken {
        val now: LocalDateTime = LocalDateTime.now()
        val nowTokenExpire = getExpireAt(now)
        val accessTokenTime: LocalDateTime = getTokenTime(now, ACCESS_TOKEN_EXPIRE_TIME.toLong())
        val refreshTokenTime: LocalDateTime = getTokenTime(now, REFRESH_TOKEN_EXPIRE_TIME.toLong())

        val accessTokenExpire = getExpireAt(accessTokenTime)
        val refreshTokenExpire = getExpireAt(refreshTokenTime)
        val accessToken = createToken(
            id = id,
            tokenExpireTime = accessTokenExpire,
        )
        var loginToken: LoginToken? = refreshTokenRepository.findByPresidentId(id)?.let {
            val refreshTokenExpireAt = getExpireAt(it.expireAt)
            if (nowTokenExpire > refreshTokenExpireAt) {
                throw PayhereException(REFRESH_TOKEN_EXPIRE)
            }
            LoginToken(
                access = accessToken,
                accessExpireAt = Date(accessTokenExpire),
                id = it.id
            )
        }
        if (loginToken == null) {
            val refreshToken = createToken(
                id = id,
                tokenExpireTime = refreshTokenExpire,
            )
            val president = presidentRepository.findByIdOrNull(id)
                ?: throw PayhereException(NOT_EXSISTS_INFO)
            val savedRefreshRefreshToken = refreshTokenRepository.save(
                RefreshToken(
                    president = president,
                    token = refreshToken,
                    expireAt = refreshTokenTime,
                )
            )
            loginToken = LoginToken(
                access = accessToken,
                accessExpireAt = Date(accessTokenExpire),
                id = savedRefreshRefreshToken.id
            )
        }
        return loginToken
    }

    private fun getExpireAt(accessTokenTime: LocalDateTime) =
        accessTokenTime.toInstant(ZoneOffset.UTC).toEpochMilli()

    private fun getTokenTime(now: LocalDateTime, expireTime: Long): LocalDateTime {
        val accessTokenTime: LocalDateTime =
            now.plus(expireTime, ChronoUnit.SECONDS)
        return accessTokenTime
    }

    fun getAuthentication(token: String): Authentication {
        val president =
            presidentRepository.findByIdOrNull(getAccount(token)) ?: throw PayhereException(
                NOT_EXSISTS_INFO
            )
        val loginUserDetail = LoginUserDetail(president.phone);
        return UsernamePasswordAuthenticationToken(loginUserDetail, "", loginUserDetail.authorities)
    }

    fun getAccount(token: String): Long {
        return Jwts.parserBuilder().setSigningKey(secretKey).build()
            .parseClaimsJws(token).body.subject.toLong()
    }

    fun validateToken(token: String): Boolean {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).body
            return true;
        } catch (e: SecurityException) {
            e.printStackTrace()
        } catch (e: ExpiredJwtException) {
            return false
        } catch (e: UnsupportedJwtException) {
            e.printStackTrace()
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }
        return false
    }

    fun resolveToken(request: HttpServletRequest?): String? {
        return request?.getHeader("Authorization")
    }

    //토큰 생성
    private fun createToken(id: Long, tokenExpireTime: Long, role: String = ""): String {
        return Jwts.builder()
            .setSubject(id.toString())
            .claim("auth", role)
            .setExpiration(Date(tokenExpireTime))
            .signWith(key, SignatureAlgorithm.HS512)
            .compact()
    }
}