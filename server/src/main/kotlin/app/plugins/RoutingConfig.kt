package app.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import app.plugins.routes.companyRoutes
import app.plugins.routes.resumeRoutes
import app.plugins.routes.vacancyRoutes
import domain.repositories.CompanyRepository
import domain.repositories.ResumeRepository
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
        }
    }
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        val companyRepository: CompanyRepository by inject()
        val resumeRepository: ResumeRepository by inject()
        companyRoutes(companyRepository)
        vacancyRoutes(companyRepository)
        resumeRoutes(resumeRepository)
    }

}
