package html_translate

import html_translate.api.controller.htmlController
import html_translate.api.service.ServiceBytes
import html_translate.config.Settings
import html_translate.integration.Translator
import html_translate.integration.impl.TranslationMemory
import html_translate.integration.impl.GoogleTranslator
import html_translate.parser.HtmlParser
import html_translate.plugins.configureSerialization
import io.ktor.http.*

import io.ktor.server.application.*
import io.ktor.server.config.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.named
import org.koin.core.module.dsl.withOptions
import org.koin.dsl.module


fun main(args: Array<String>) = io.ktor.server.netty.EngineMain.main(args)

fun Application.controller() {
    configureSerialization()
    htmlController()
}

fun Application.koin() {
    startKoin {
        modules(
            listOf(
                settingsModule(environment.config),
                serviceBytesModule(),
                parserModule(),
                translatorModule(),
            )
        )
    }
}

fun settingsModule(config: ApplicationConfig): Module = module {
    single { Settings(config) }
}

fun serviceBytesModule(): Module = module {
    single { ServiceBytes() }
}

fun parserModule(): Module = module {
    single { HtmlParser() }
}

fun translatorModule(): Module = module {
    single<Translator> { TranslationMemory() } withOptions { named("Class_TranslationMemory")}
    single<Translator> { GoogleTranslator() } withOptions { named("Class_GoogleTranslator")}
}

fun Application.exceptions() {
    install(StatusPages) {
        exception<CustomException> { call, cause ->
            call.respondText(cause.message!!, status = HttpStatusCode.InternalServerError)
        }
    }
}

class CustomException(message: String): Exception(message)