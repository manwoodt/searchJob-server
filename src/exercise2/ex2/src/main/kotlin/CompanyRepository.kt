import kotlinx.serialization.Serializable

@Serializable
data class Company(
    val name: String,
    val fieldOfActivity: String,
    val contacts: String,
    val vacancies: List<String> = emptyList()
)


object CompanyRepository {
    private val companies = listOf(
        Company("Yandex", "IT", "contact@yandex.com"),
        Company("Sber", "Banking", "info@sber.com"),
        Company("City Services", "Public services", "support@cityservices.com")
    )

    fun getAllCompanies(): List<Company> {
        return companies
    }
    fun getCompanyById(id:Int):Company?{
        return companies.getOrNull(id)
    }
}