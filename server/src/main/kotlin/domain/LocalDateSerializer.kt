package domain

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializer
import kotlinx.serialization.KSerializer
import kotlinx.serialization.encoding.Decoder
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalSerializationApi::class)
@Serializer (forClass = LocalDate:: class)
object LocalDateSerializer : KSerializer<LocalDate> {
    private val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

    override fun deserialize(decoder: Decoder): LocalDate {
        return LocalDate.parse(decoder.decodeString(), formatter)
    }
}
