package models

import serializers.LocalDateSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import models.enums.*
import java.time.LocalDate

@Serializable
data class Resume(
    @SerialName("candidate_info")
    val candidateInfo: CandidateInformation,
    val education: List<CandidateEducation>,
    @SerialName("job_experience")
    val jobExperience: List<CandidateJobExperience>,
    @SerialName("free_form")
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
data class CandidateJobExperience(
    @SerialName ("date_start")
    val dateStart: String,
    @SerialName ("date_end")
    val dateEnd: String,
    @SerialName ("company_name")
    val companyName: String,
    val description: String
)


@Serializable
data class Contacts(
    val phone: String,
    val email: String
)







