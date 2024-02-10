package product.management.app.product.utils

class Barcode {

    companion object {
        fun create(
            prefix: String,
            manufacturerCode: String,
            productIdentifier: String
        ): String {
            val barcodeWithoutCheckDigit = prefix + manufacturerCode + productIdentifier
            val checkDigit = calculateCheckDigit(barcodeWithoutCheckDigit)
            return barcodeWithoutCheckDigit + checkDigit
        }

        private fun calculateCheckDigit(barcode: String): Char {
            var sum = 0
            for (i in barcode.indices) {
                val digit = barcode[i] - '0'
                sum += if (i % 2 == 0) digit else digit * 3
            }
            val checkDigit = (10 - sum % 10) % 10
            return ('0' + checkDigit)
        }
    }
}