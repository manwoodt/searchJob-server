package domain.models.enums

import kotlinx.serialization.Serializable

@Serializable
enum class Profession(val displayName: String) {
    DEV("developer"),
    QA("QA"),
    PM("pm"),
    ANAL("analyst"),
    PD("project designer"),
    ALL("all")
}