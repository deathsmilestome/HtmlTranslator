package html_translate.api.controller

import html_translate.CustomException
import html_translate.api.service.ServiceBytes
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import org.koin.ktor.ext.get

fun Application.htmlController() {
    val serviceBytes: ServiceBytes = get()


    routing {
        get("/") {
            call.respondText("dstm")
        }

        post("/upload") {
            val bytes = call.receive<ByteArray>()
            val langFrom = call.request.queryParameters["langFrom"] ?: throw CustomException("bad request: parameter (langFrom) is not set")
            val langTo = call.request.queryParameters["langTo"] ?: throw CustomException("bad request: parameter (langTo) is not set")
            call.respond(message = serviceBytes.translate(bytes, langFrom, langTo))
        }
    }
}