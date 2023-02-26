package html_translate.parser

class HtmlParser {

    fun switchTagToAnchor(html: String): Pair<String, Map<String, String>> {
        val dictionary = mutableMapOf<String,String>()
        val newHtml = StringBuilder()
        val tag = StringBuilder()
        var isTag = false
        var counter = 1
        for (char in html) {
            when {
                char == '<' -> {
                    isTag = true
                    tag.append(char)
                }
                char == '>' -> {
                    isTag = false
                    tag.append(char)
                    val key = "$-${counter}-$"
                    dictionary[key] = tag.toString()
                    newHtml.append(key)
                    counter++
                    tag.clear()
                }
                isTag -> tag.append(char)
                else -> newHtml.append(char)
            }
        }
        return newHtml.toString().replace(Regex("""[\r\n]+"""), "%0D").replace(Regex("""\s"""), "%20") to dictionary
    }

    fun switchAnchorToTag(html: String, dictionary: Map<String, String>): String {
        var result = html
        dictionary.forEach{ result = result.replace(it.key, it.value) }
        return result.replace("%20", " ").replace("%0D", "\r\n")
    }
}