package com.example.habittracker.cloud

import com.example.habittracker.data.Habit
import com.example.habittracker.data.UidDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT

interface CloudService {
    @GET("habit")
    suspend fun getHabits(@Header("Authorization") token: String): List<Habit>

    @PUT("habit")
    suspend fun putHabit(@Header("Authorization") token: String, @Body habit: Habit): UidDto
}