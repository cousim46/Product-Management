package product.management.app.product.enums

enum class Size {
    SMALL, LARGE;

    companion object {
        fun contains(size: Size) : Boolean {
            return size in Size.values()
        }
    }
}
