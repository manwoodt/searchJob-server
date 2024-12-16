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