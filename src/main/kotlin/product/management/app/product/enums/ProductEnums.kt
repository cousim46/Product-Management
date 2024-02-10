package product.management.app.product.enums

enum class Size {
    SMALL, LARGE;

    companion object {
        fun contains(size: String) : Boolean {
            val value = entries.find { it.name == size }
            if(value == null ) {
                return false
            }
            return true
        }
    }
}
