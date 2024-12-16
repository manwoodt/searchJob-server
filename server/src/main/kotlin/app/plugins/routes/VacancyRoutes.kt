package app.plugins.routes

import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import domain.repositories.CompanyRepository
import io.ktor.server.application.call

fun Route.vacancyRoutes(repository: CompanyRepository){
    get("/vacancies") {
        val vacancies = repository.getAllVacancies()
        call.respond(vacancies)
    }

    get("/vacancies/{id}") {
        val id = call.parameters["id"]?.toIntOrNull()
        if (id != null) {
            val vacancy = repository.getVacancyById(id)
            if (vacancy != null)
                call.respond(vacancy)
            else
                call.respondText("", status = HttpStatusCode.NoContent)
        } else
            call.respondText("", status = HttpStatusCode.BadRequest)
    }
}