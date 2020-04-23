package com.example.habittracker.data

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type

class HabitJsonSerializer : JsonSerializer<Habit> {
    override fun serialize(
        src: Habit,
        typeOfSrc: Type,
        context: JsonSerializationContext
    ): JsonElement = JsonObject().apply {
        addProperty("count", src.habitCount)
        addProperty("date", src.date)
        addProperty("description", src.description)
        addProperty("frequency", src.habitFrequency)
        addProperty("priority", PriorityConverter().fromPriority(src.priority))
        addProperty("title", src.name)
        addProperty("type", HabitTypeConverter().fromHabitType(src.habitType))
        if (src.uid != null)
            addProperty("uid", src.uid)
    }

}