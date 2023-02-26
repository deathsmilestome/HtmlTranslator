package html_translate.integration

interface Translator {
    fun translate(text: String, langFrom: String, langTo: String): String?
}