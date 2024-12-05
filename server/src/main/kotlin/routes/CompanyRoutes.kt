package routes

import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import repositories.CompanyRepository

fun Route.companyRoutes(){
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