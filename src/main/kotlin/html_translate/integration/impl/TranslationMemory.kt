package html_translate.integration.impl

import com.fasterxml.jackson.databind.ObjectMapper
import html_translate.config.Settings
import html_translate.integration.Translator
import okhttp3.OkHttpClient
import okhttp3.Request
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class TranslationMemory : KoinComponent, Translator {
    private val settings: Settings = get()
    private val objectMapper = ObjectMapper()

    override fun translate(text: String, langFrom: String, langTo: String): String? {

        val client = OkHttpClient()

        val request = Request.Builder()
            .url("https://translated-mymemory---translation-memory.p.rapidapi.com/get?langpair=$langFrom%7C$langTo&q=$text&mt=1&onlyprivate=0&de=a%40b.c")
            .get()
            .addHeader("X-RapidAPI-Key", settings.rapidApiKey)
            .addHeader("X-RapidAPI-Host", "translated-mymemory---translation-memory.p.rapidapi.com")
            .build()

        val response = client.newCall(request).execute()
        return objectMapper.readTree(response.body()?.bytes())["responseData"]["translatedText"].asText()

    }
}