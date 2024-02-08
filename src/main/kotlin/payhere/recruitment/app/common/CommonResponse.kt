package payhere.demo.app.common

data class CommonResponse(
    val meta: Meta,
    val data: Data?
) {
    companion object {
        fun toResponse(meta: Meta = Meta.of(), data: Data? = null): CommonResponse {
            return CommonResponse(
                meta = meta,
                data = data,
            )
        }
    }
}

data class Meta(
    val code: Int,
    val message: String,
) {
    companion object {
        const val DEFAULT_SUCCESS_STATUS_CODE = 200
        const val DEFAULT_SUCCESS_MESSAGE = "ok"

        fun of(code: Int = DEFAULT_SUCCESS_STATUS_CODE, message: String =DEFAULT_SUCCESS_MESSAGE) : Meta {
            return Meta(
                code = code,
                message = message,
            )
        }
    }
}

data class Data(
    val products: Any
) {
    companion object {
        fun of(products: Any): Data {
            return Data(
                products = products,
            )
        }
    }
}
