package models.enums

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class WillingnessOfRelocation {
    @SerialName("preferable")
    PREFERABLE,

    @SerialName("possible")
    POSSIBLE,

    @SerialName("impossible")
    IMPOSSIBLE
}