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