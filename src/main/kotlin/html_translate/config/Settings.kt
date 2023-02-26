package html_translate.config

import io.ktor.server.config.*

class Settings(private val config: ApplicationConfig) {
    val translator = Translator()

    inner class Translator() {
        val type = getQualifierByType()

        private fun getQualifierByType() = when (config.property("translator.type").getString()) {
            "memory" -> "Class_TranslationMemory"
            "google" -> "Class_GoogleTranslator"
            else -> throw IllegalArgumentException("unsupported translator type")
        }
    }

    val rapidApiKey = config.property("xrapid.api").getString()
}