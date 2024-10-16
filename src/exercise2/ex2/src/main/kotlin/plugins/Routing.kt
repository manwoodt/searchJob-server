package plugins

import CompanyRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

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
        get("/companies") {
            val companies = CompanyRepository.getAllCompanies()
            call.respond(companies)
        }
        get("/companies/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id != null) {
                val company = CompanyRepository.getCompanyById(id)
                if (company != null)
                    call.respond(company)
                else
                    call.respondText("", status = HttpStatusCode.NoContent)
            } else
                call.respondText("", status = HttpStatusCode.BadRequest)
        }
    }
}
