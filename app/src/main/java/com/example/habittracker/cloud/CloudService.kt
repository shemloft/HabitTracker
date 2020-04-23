package com.example.habittracker.cloud

import com.example.habittracker.data.Habit
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT

interface CloudService {
    @GET("habit")
    fun getHabits(@Header("Authorization") token: String): List<Habit>

    @PUT("habit")
    fun putHabit(@Header("Authorization") token: String, habit: Habit)
}