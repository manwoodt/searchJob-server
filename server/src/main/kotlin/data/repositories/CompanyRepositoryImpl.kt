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