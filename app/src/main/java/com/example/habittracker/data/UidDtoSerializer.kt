package com.example.habittracker.data

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type

class UidDtoSerializer : JsonSerializer<UidDto> {
    override fun serialize(
        src: UidDto,
        typeOfSrc: Type,
        context: JsonSerializationContext
    ): JsonElement = JsonObject().apply {
        addProperty("uid", src.uid)
    }
}