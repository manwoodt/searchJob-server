package routes

import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import repositories.CompanyRepository

fun Route.vacancyRoutes(){
    get("/vacancies") {
        val vacancies = CompanyRepository.getAllVacancies()
        call.respond(vacancies)
    }

    get("/vacancies/{id}") {
        val id = call.parameters["id"]?.toIntOrNull()
        if (id != null) {
            val vacancy = CompanyRepository.getVacancyById(id)
            if (vacancy != null)
                call.respond(vacancy)
            else
                call.respondText("", status = HttpStatusCode.NoContent)
        } else
            call.respondText("", status = HttpStatusCode.BadRequest)
    }
}