object CompanyRepository {
    private val companies = listOf(
        Company("Yandex", "IT", "contact@yandex.com"),
        Company("Sber", "Banking", "info@sber.com"),
        Company("City Services", "Public services", "support@cityservices.com")
    )

    fun getAllCompanies(): List<Company> {
        return companies
    }
}