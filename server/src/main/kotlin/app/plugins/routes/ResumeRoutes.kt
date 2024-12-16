package app.plugins.routes

import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import domain.models.Resume
import domain.repositories.ResumeRepository
import io.ktor.server.application.call

fun Route.resumeRoutes(repository: ResumeRepository){
    get("/resumes"){
        val resumes = repository.getResumes()
        call.respond(resumes)
    }

    get("/resumes/{id}") {
        val id = call.parameters["id"]?.toIntOrNull()
        if (id != null) {
            val resume = repository.getResumeById(id)
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
            resume.tags = repository.createTags(resume)
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
            val resume = repository.getResumeById(id)
            if (resume != null)
                call.respond(resume.tags)
            else
                call.respondText("", status = HttpStatusCode.NoContent)
        } else
            call.respondText("", status = HttpStatusCode.BadRequest)
    }

}