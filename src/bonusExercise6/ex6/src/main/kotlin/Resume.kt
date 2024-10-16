
import com.kotlin.LocalDateSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Serializable
data class Resume(
    @SerialName ("candidate_info")
    val candidateInfo: CandidateInformation,
    val education: List<CandidateEducation>,
    @SerialName ("job_experience")
    val jobExperience: List<CandidateJobExperience>,
    @SerialName ("free_form")
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
enum class Profession(val displayName: String) {
    DEV("developer"),
    QA("QA"),
    PM("pm"),
    ANAL("analyst"),
    PD("project designer"),
    ALL("all")
}

@Serializable
enum class WillingnessOfRelocation {
    @SerialName("preferable")
    PREFERABLE,

    @SerialName("possible")
    POSSIBLE,

    @SerialName("impossible")
    IMPOSSIBLE
}

@Serializable
data class Contacts(
    val phone: String,
    val email: String
)

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
enum class TypeOfEducation {
    @SerialName("higher")
    HIGHER,
    @SerialName("secondary special")
    SECONDARY_SPECIAL,
    @SerialName("secondary")
    SECONDARY
}

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



object ResumeRepository {
    private val resumes = listOf(
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

    fun getResumes(): List<Resume>{
        return resumes
    }

    fun getResumeById(id: Int): Resume? {
        return resumes.getOrNull(id)
    }

    fun createTags(resume: Resume): List<String> {
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