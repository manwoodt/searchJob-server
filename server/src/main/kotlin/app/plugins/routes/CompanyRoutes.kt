package app.plugins.routes

import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import domain.repositories.CompanyRepository

fun Route.companyRoutes(repository: CompanyRepository){
    get("/companies") {
        val companies = repository.getAllCompanies()
        call.respond(companies)
    }

    get("/companies/{id}") {
        val id = call.parameters["id"]?.toIntOrNull()
        if (id != null) {
            val company = repository.getCompanyById(id)
            if (company != null)
                call.respond(company)
            else
                call.respondText("", status = HttpStatusCode.NoContent)
        } else
            call.respondText("", status = HttpStatusCode.BadRequest)
    }
}