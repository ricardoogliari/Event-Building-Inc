package com.eventbuilding.data

import retrofit2.http.GET

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Path

private const val BASE_URL = "https://swensonhe-dev-challenge.s3.us-west-2.amazonaws.com/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface SwensonheService {
    @GET("categories.json")
    suspend fun getCategories(): List<Category>

    @GET("categories/{categoryId}.json")
    suspend fun getItens(@Path("categoryId") categoryId: String): List<Item>
}

object SwensonheApi {
    val retrofitService: SwensonheService by lazy {
        retrofit.create(SwensonheService::class.java)
    }
}