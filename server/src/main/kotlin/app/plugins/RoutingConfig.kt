package app.plugins

import ResumeRepositoryImpl
import data.repositories.CompanyRepositoryImpl
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import app.plugins.routes.companyRoutes
import app.plugins.routes.resumeRoutes
import app.plugins.routes.vacancyRoutes

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
        val companyRepository = CompanyRepositoryImpl()
        val resumeRepository = ResumeRepositoryImpl()
        companyRoutes(companyRepository)
        vacancyRoutes(companyRepository)
        resumeRoutes(resumeRepository)
    }

}
