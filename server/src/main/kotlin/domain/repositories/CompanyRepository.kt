package domain.repositories

import domain.models.Company
import domain.models.Vacancy

interface CompanyRepository {
    fun getAllCompanies(): List<Company>
    fun getCompanyById(id: Int): Company?

    fun getAllVacancies(): List<Vacancy>
    fun getVacancyById(id: Int): Vacancy?


}