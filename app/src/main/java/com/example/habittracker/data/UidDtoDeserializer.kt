package com.example.habittracker.data

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class UidDtoDeserializer : JsonDeserializer<UidDto> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): UidDto = UidDto(
        json.asJsonObject.get("uid").asString
    )
}