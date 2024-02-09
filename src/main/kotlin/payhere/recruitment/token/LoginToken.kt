package payhere.recruitment.token

import java.util.*

data class LoginToken(
    val id: Long,
    val access: String,
    val accessExpireAt: Date,
)
