package html_translate.api.service

import html_translate.CustomException
import html_translate.config.Settings
import html_translate.integration.Translator
import html_translate.parser.HtmlParser
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.qualifier.StringQualifier


class ServiceBytes: KoinComponent {
    private val settings: Settings = get()
    private val parser: HtmlParser = get()
    private val translator: Translator = get(StringQualifier(settings.translator.type))


    fun translate(bytes: ByteArray, langFrom: String, langTo: String): String {

        val rawText = bytes.decodeToString()
        val (preparedText, dictionary) = parser.switchTagToAnchor(rawText)
        val translatedText = translator.translate(preparedText, langFrom, langTo) ?: throw CustomException("translation troubles")

        return parser.switchAnchorToTag(translatedText, dictionary)
    }
}