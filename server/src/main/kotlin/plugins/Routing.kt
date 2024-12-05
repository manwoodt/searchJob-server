package plugins

import CompanyRepository
import Resume
import ResumeRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.request.*
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

        get("/resumes"){
            val resumes = ResumeRepository.getResumes()
            call.respond(resumes)
        }

        get("/resumes/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id != null) {
                val resume = ResumeRepository.getResumeById(id)
                if (resume != null)
                    call.respond(resume)
                else
                    call.respondText("", status = HttpStatusCode.NoContent)
            } else
                call.respondText("", status = HttpStatusCode.BadRequest)
        }

        post("/resumes/{id}"){
        val id = call.parameters["id"]?.toIntOrNull()
            if (id != null){
                val resume = call.receive<Resume>()
                resume.tags = ResumeRepository.createTags(resume)
                println("\nReceived resume for ID $id:\n $resume\n")
                call.respond(HttpStatusCode.OK)
            }
            else{
                call.respondText("",status = HttpStatusCode.BadRequest)
            }
        }
        get("/resumes/{id}/tags"){
            val id = call.parameters["id"]?.toIntOrNull()
            if (id != null) {
                val resume = ResumeRepository.getResumeById(id)
                if (resume != null)
                    call.respond(resume.tags)
                else
                    call.respondText("", status = HttpStatusCode.NoContent)
            } else
                call.respondText("", status = HttpStatusCode.BadRequest)
        }

    }
}
