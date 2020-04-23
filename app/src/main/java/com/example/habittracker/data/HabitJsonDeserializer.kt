package com.example.habittracker.data

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class HabitJsonDeserializer : JsonDeserializer<Habit> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Habit = Habit(
        json.asJsonObject.get("title").asString,
        json.asJsonObject.get("description").asString,
        PriorityConverter().toPriority(json.asJsonObject.get("priority").asInt),
        HabitTypeConverter().toHabitType(json.asJsonObject.get("type").asInt),
        json.asJsonObject.get("count").asInt,
        json.asJsonObject.get("frequency").asInt
    ).apply {
        this.date = json.asJsonObject.get("date").asLong
        this.uid = json.asJsonObject.get("uid").asString
    }
}