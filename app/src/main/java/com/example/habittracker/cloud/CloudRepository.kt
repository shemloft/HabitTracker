package com.example.habittracker.cloud

import android.content.Context
import com.example.habittracker.R
import com.example.habittracker.data.*
import com.example.habittracker.model.Model
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CloudRepository {
    private val gson = GsonBuilder()
        .registerTypeAdapter(Habit::class.java, HabitJsonSerializer())
        .registerTypeAdapter(Habit::class.java, HabitJsonDeserializer())
        .registerTypeAdapter(UidDto::class.java, UidDtoSerializer())
        .registerTypeAdapter(UidDto::class.java, UidDtoDeserializer())
        .create()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://droid-test-server.doubletapp.ru/api/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    private val service = retrofit.create(CloudService::class.java)

    private lateinit var context: Context

    fun addContext(context: Context) {
        this.context = context
    }

    suspend fun addHabit(habit: Habit) {
        val token = context.getString(R.string.token);
        service.putHabit(token, habit)
    }

    suspend fun saveDatabase() {
        val token = context.getString(R.string.token)
        Model.getAllHabits().forEach { habit ->
            val uidDto = service.putHabit(token, habit)
            habit.uid = uidDto.uid
            Model.updateHabit(habit)
        }
    }

    suspend fun loadDatabase() {
        val token = context.getString(R.string.token)
        val habits = service.getHabits(token)
        Model.replaceAllHabits(habits)
    }
}