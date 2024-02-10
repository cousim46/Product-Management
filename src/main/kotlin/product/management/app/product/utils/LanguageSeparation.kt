package product.management.app.product.utils

class LanguageSeparation {

    companion object {
        val prefixSet: Array<String> = arrayOf(
            "ㄱ", "ㄲ", "ㄴ", "ㄷ", "ㄸ",
            "ㄹ", "ㅁ", "ㅂ", "ㅃ", "ㅅ",
            "ㅆ", "ㅇ", "ㅈ", "ㅉ", "ㅊ",
            "ㅋ", "ㅌ", "ㅍ", "ㅎ"
        )
        val prefixBaseUnicode = 0xAC00
        val medialSize = 28
        val finalSize = 21
        val EMPTY_STR = ""
        fun extractPrefix(target: String): String {
            var extractPrefix = EMPTY_STR
            if(target.isNotEmpty()) {
                for(targetPart in target) {
                    extractPrefix += result(targetPart)
                }
            }
            return extractPrefix
        }

        fun result(targetPart: Char) : String {
            if (targetPart.code >= prefixBaseUnicode) {
                val uniVal = targetPart.code - prefixBaseUnicode
                val cho = ((uniVal - (uniVal % medialSize)) / medialSize) / finalSize

                return prefixSet.get(cho)
            }
            return EMPTY_STR
        }
    }
}