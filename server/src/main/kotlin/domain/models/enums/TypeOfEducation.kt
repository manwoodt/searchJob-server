package domain.models.enums

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class TypeOfEducation {
    @SerialName("higher")
    HIGHER,
    @SerialName("secondary special")
    SECONDARY_SPECIAL,
    @SerialName("secondary")
    SECONDARY
}