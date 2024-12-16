package app

import app.plugins.configureSerialization
import io.ktor.server.application.*
import io.ktor.server.netty.*
import app.plugins.configureRouting
import io.ktor.server.engine.embeddedServer
import org.koin.core.context.startKoin

fun main(args: Array<String>) {
    startKoin {
        modules(appModule)
    }

    embeddedServer(Netty, port = 8080, host = "127.0.0.1") {
        module()
    }.start(wait = true)

}

fun Application.module() {
    configureSerialization()
    configureRouting()
}
