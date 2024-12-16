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