package com.example.habittracker.cloud

import retrofit2.Retrofit
import retrofit2.create

object CloudRepository {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://droid-test-server.doubletapp.ru/api/")
        .build()

    val service = retrofit.create(CloudService::class.java)

}