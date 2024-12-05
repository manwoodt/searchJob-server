package repositories

import models.Company
import models.Vacancy


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

    fun getAllCompanies(): List<Company> {
        return companies
    }
    fun getAllVacancies():List<Vacancy>{
        return companies.flatMap { it.vacancies }
    }

    fun getVacancyById(id:Int):Vacancy?{
        val vacancies = getAllVacancies()
        return vacancies.find { it.id == id}
    }

    fun getCompanyById(id:Int):Company?{
        return companies.find { it.id == id}
    }
}