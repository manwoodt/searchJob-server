import kotlinx.serialization.Serializable

@Serializable
data class Company(
    val id:Int,
    val name: String,
    val fieldOfActivity: String,
    val vacancies: List<Vacancy>,
    val contacts: String
)

@Serializable
data class Vacancy(
    val id:Int,
    val profession: String,
    val level: String,
    val salary: Int,
    val description: String
)

object CompanyRepository {
    private val companies: List<Company> = listOf(
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
                Vacancy(1, "developer", "middle", 150000, "Android!"),
                Vacancy(2, "designer", "middle", 100000, "Looking for 5 weeks"),
                Vacancy(3, "pm", "senior", 260000, "...")
            ),
            "89265353235"
        ),
        Company(
            3,
            "VK",
            "Public services",
            listOf(
                Vacancy(1, "analyst", "junior", 70000, " intern"),
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

    fun getAllCompanies(): List<Company> {
        return companies
    }
    fun getCompanyById(id:Int):Company?{
        return companies.getOrNull(id)
    }
}