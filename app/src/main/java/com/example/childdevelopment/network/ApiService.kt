package com.example.childdevelopment.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers

private const val BASE_URL = "https://cs493finalproject.wm.r.appspot.com"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface ApiService {
    @Headers("Content-Type: application/json")
    @GET("milestones")
    suspend fun getMilestones(): List<MilestonesOption>
}

object MilestoneApi {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}