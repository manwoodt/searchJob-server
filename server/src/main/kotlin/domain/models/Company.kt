package domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Company(
    val id:Int,
    val name: String,
    val fieldOfActivity: String,
    val vacancies: List<Vacancy>,
    val contacts: String
)