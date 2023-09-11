package com.remal.collectsitesapp.api

import android.util.Log
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface GoogleApi {
    @GET("/complete/search")
    fun getSuggestion(
        @Query("output") output: String = "firefox",
        @Query("q") q: String): Call<JsonArray>

    class ListingResponse(val data: ListingData)

    class ListingData(
        val children: List<GoogleSuggestionResponse>
    )

    data class GoogleSuggestionResponse(val data: GoogleSuggestion)

    companion object {
        private const val BASE_URL = "https://google.com/"
        fun create(): GoogleApi = create(HttpUrl.parse(BASE_URL)!!)
        fun create(httpUrl: HttpUrl): GoogleApi {
            val logger = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
                Log.d("API", it)
            })
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()
            return Retrofit.Builder()
                .baseUrl(httpUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GoogleApi::class.java)
        }
    }
}