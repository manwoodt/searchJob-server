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
package app

import ResumeRepositoryImpl
import data.repositories.CompanyRepositoryImpl
import domain.repositories.CompanyRepository
import domain.repositories.ResumeRepository
import org.koin.dsl.module

val appModule = module {
    single<CompanyRepository> { CompanyRepositoryImpl() }
    single<ResumeRepository> { ResumeRepositoryImpl() }
}
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
package app.plugins

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json()
    }
}
package app.plugins.routes

import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import domain.repositories.CompanyRepository
import io.ktor.server.application.call

fun Route.companyRoutes(repository: CompanyRepository){
    get("/companies") {
        call.respond(repository.getAllCompanies())
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
package data.hardCodedInformation

import domain.models.Company
import domain.models.Vacancy

object HardCodedCompanies {
     val companies: List<Company> = listOf(
        Company(
            1,
            "Sber",
            "Banking",
            listOf(
                Vacancy(1, "qa", "middle", 180000, "Very interesting job"),
                Vacancy(2, "developer", "senior", 400000, "High priority!")
            ),
            "89191234327"
        ),
        Company(
            2,
            "Yandex",
            "IT",
            listOf(
                Vacancy(3, "developer", "middle", 150000, "Android!"),
                Vacancy(4, "designer", "middle", 100000, "Looking for 5 weeks"),
                Vacancy(5, "pm", "senior", 260000, "...")
            ),
            "89265353235"
        ),
        Company(
            3,
            "VK",
            "Public services",
            listOf(
                Vacancy(6, "analyst", "junior", 70000, " intern"),
            ),
            "88005326737"
        ),
        Company(
            4,
            "Tinkoff",
            "Banking",
            emptyList(),
            "89021237654"
        )
    )
}
package data.hardCodedInformation

import domain.models.CandidateEducation
import domain.models.CandidateInformation
import domain.models.CandidateJobExperience
import domain.models.Contacts
import domain.models.Resume
import domain.models.enums.Profession
import domain.models.enums.TypeOfEducation
import domain.models.enums.WillingnessOfRelocation
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object HardCodedResumes {
    val resumes = listOf(
        Resume(
            candidateInfo = CandidateInformation(
                fullName = "Vasiliev Sergei Petrovich",
                profession = Profession.QA,
                sex = "male",
                birthDate = LocalDate.parse("1998-09-30", DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                contacts = Contacts(
                    phone = "72938572843",
                    email = "vspetrovich@pochta.ru"
                ),
                relocation = WillingnessOfRelocation.POSSIBLE
            ),
            education = listOf(
                CandidateEducation(TypeOfEducation.HIGHER, "2017", "2021", "Mathematics in state university"),
                CandidateEducation(TypeOfEducation.SECONDARY_SPECIAL, "2013", "2017", "College of informatics"),
                CandidateEducation(TypeOfEducation.SECONDARY, "2005", "2013", "Lyceum 156")
            ),
            jobExperience = listOf(
                CandidateJobExperience(dateStart = "08.2021", dateEnd = "04.2022", companyName = "FinTech", description = "Some fintech company creating a business platform"),
                CandidateJobExperience(dateStart = "05.2022", dateEnd = "01.2023", companyName = "SoftProm", description = "Typical galley")
            ),
            freeForm = "I'm a QA specialist from head to heels.",
            tags= listOf("Some tags are here")
        )
    )
}
package data.repositories

import data.hardCodedInformation.HardCodedCompanies
import domain.repositories.CompanyRepository
import domain.models.Company
import domain.models.Vacancy


class CompanyRepositoryImpl: CompanyRepository {

    private val hardCodedCompanies = HardCodedCompanies

    override fun getAllCompanies(): List<Company> {
        return hardCodedCompanies.companies
    }

    override  fun getCompanyById(id:Int): Company?{
        return hardCodedCompanies.companies.find { it.id == id}
    }

    override  fun getAllVacancies():List<Vacancy>{
        return hardCodedCompanies.companies.flatMap { it.vacancies }
    }

    override   fun getVacancyById(id:Int): Vacancy?{
        val vacancies = getAllVacancies()
        return vacancies.find { it.id == id}
    }

}
import data.hardCodedInformation.HardCodedCompanies
import data.hardCodedInformation.HardCodedResumes
import domain.models.Resume
import domain.models.enums.Profession
import domain.models.enums.TypeOfEducation
import domain.repositories.ResumeRepository

class ResumeRepositoryImpl: ResumeRepository {

    private val hardCodedResumes = HardCodedResumes

    override fun getResumes(): List<Resume>{
        return hardCodedResumes.resumes
    }

    override fun getResumeById(id: Int): Resume? {
        return hardCodedResumes.resumes.getOrNull(id-1)
    }

    override fun createTags(resume: Resume): List<String> {
        val tags = mutableListOf<String>()

        if (resume.candidateInfo.profession == Profession.DEV) {
            tags.add("Developer")
        }
        if (resume.education.any { it.type == TypeOfEducation.HIGHER }) {
            tags.add("Higher Education")
        }
        if (resume.jobExperience.isNotEmpty()) {
            tags.add("Experienced")
        }

        println("Created tags for resume: $tags")

        return tags
    }

}
import data.hardCodedInformation.HardCodedResumes
import domain.models.Resume
import domain.models.enums.Profession
import domain.models.enums.TypeOfEducation
import domain.repositories.ResumeRepository

class ResumeRepositoryImpl: ResumeRepository {

    private val hardCodedResumes = HardCodedResumes

    override fun getResumes(): List<Resume>{
        return hardCodedResumes.resumes
    }

    override fun getResumeById(id: Int): Resume? {
        return hardCodedResumes.resumes.getOrNull(id-1)
    }

    override fun createTags(resume: Resume): List<String> {
        val tags = mutableListOf<String>()

        if (resume.candidateInfo.profession == Profession.DEV) {
            tags.add("Developer")
        }
        if (resume.education.any { it.type == TypeOfEducation.HIGHER }) {
            tags.add("Higher Education")
        }
        if (resume.jobExperience.isNotEmpty()) {
            tags.add("Experienced")
        }

        println("Created tags for resume: $tags")

        return tags
    }

}
package domain

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializer
import kotlinx.serialization.KSerializer
import kotlinx.serialization.encoding.Decoder
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalSerializationApi::class)
@Serializer (forClass = LocalDate:: class)
object LocalDateSerializer : KSerializer<LocalDate> {
    private val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

    override fun deserialize(decoder: Decoder): LocalDate {
        return LocalDate.parse(decoder.decodeString(), formatter)
    }
}
package domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Company(
    val id:Int,
    val name: String,
    val fieldOfActivity: String,
    val vacancies: List<Vacancy>,
    val contacts: String
)
package domain.models

import domain.models.enums.Profession
import domain.models.enums.TypeOfEducation
import domain.models.enums.WillingnessOfRelocation
import domain.LocalDateSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class Resume(
    @SerialName("candidate_info")
    val candidateInfo: CandidateInformation,
    val education: List<CandidateEducation>,
    @SerialName("job_experience")
    val jobExperience: List<CandidateJobExperience>,
    @SerialName("free_form")
    val freeForm: String,
    var tags:List<String> = listOf<String>()
)

@Serializable
data class CandidateInformation(
    @SerialName ("name")
    val fullName: String,
    val profession: Profession,
    val sex: String,
    @SerialName ("birth_date")
    @Serializable(with = LocalDateSerializer::class)
    val birthDate: LocalDate,
    val contacts: Contacts,
    val relocation: WillingnessOfRelocation
){
    override fun toString():String{
        return "Candidate_info(name=$fullName, profession=$profession, sex=$sex, birth_date=$birthDate, contacts=$contacts, relocation=${relocation.toString().lowercase()})"
    }
}

@Serializable
data class CandidateEducation(
    val type: TypeOfEducation,
    @SerialName ("year_start")
    val yearStart: String,
    @SerialName ("year_end")
    val yearEnd: String,
    val description: String
)
{override fun toString():String{
    return  "Education(type=${type.toString().lowercase()}, years=${yearEnd.toInt()-yearStart.toInt()},description=$description)"
}}

@Serializable
data class CandidateJobExperience(
    @SerialName ("date_start")
    val dateStart: String,
    @SerialName ("date_end")
    val dateEnd: String,
    @SerialName ("company_name")
    val companyName: String,
    val description: String
)


@Serializable
data class Contacts(
    val phone: String,
    val email: String
)







package domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Vacancy(
    val id:Int,
    val profession: String,
    val level: String,
    val salary: Int,
    val description: String
)
package domain.models.enums

import kotlinx.serialization.Serializable

@Serializable
enum class Profession(val displayName: String) {
    DEV("developer"),
    QA("QA"),
    PM("pm"),
    ANAL("analyst"),
    PD("project designer"),
    ALL("all")
}
package domain.models.enums

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class TypeOfEducation {
    @SerialName("higher")
    HIGHER,
    @SerialName("secondary special")
    SECONDARY_SPECIAL,
    @SerialName("secondary")
    SECONDARY
}
package domain.models.enums

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class WillingnessOfRelocation {
    @SerialName("preferable")
    PREFERABLE,

    @SerialName("possible")
    POSSIBLE,

    @SerialName("impossible")
    IMPOSSIBLE
}
package domain.repositories

import domain.models.Company
import domain.models.Vacancy

interface CompanyRepository {
    fun getAllCompanies(): List<Company>
    fun getCompanyById(id: Int): Company?

    fun getAllVacancies(): List<Vacancy>
    fun getVacancyById(id: Int): Vacancy?


}
package domain.repositories

import domain.models.Resume

interface ResumeRepository {
    fun getResumes(): List<Resume>

    fun getResumeById(id: Int): Resume?

    fun createTags(resume: Resume): List<String>
}
package com.example

import com.example.plugins.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.*

class ApplicationTest {
    @Test
    fun testRoot() = testApplication {
        application {
            configureRouting()
        }
        client.get("/").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals("Hello World!", bodyAsText())
        }
    }
}
