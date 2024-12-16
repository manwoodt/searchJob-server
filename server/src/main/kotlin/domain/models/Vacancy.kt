package domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Vacancy(
    val id:Int,
    val profession: String,
    val level: String,
    val salary: Int,
    val description: String
)