package com.aikeyboard

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

data class Prediction(
    val text: String,
    val confidence: Double
)

data class AutocorrectResult(
    val original: String,
    val corrected: String,
    val changes: Int
)

data class TranslationResult(
    val original: String,
    val translated: String,
    val sourceLanguage: String,
    val targetLanguage: String
)

data class ShortcutsResponse(
    val shortcuts: List<Shortcut>
)

data class Shortcut(
    val id: String,
    val trigger: String,
    val expansion: String
)

interface ApiService {
    @GET("predictions/predict")
    suspend fun getPredictions(
        @Query("text") text: String,
        @Query("language") language: String = "en"
    ): List<Prediction>
    
    @POST("autocorrect/correct")
    suspend fun correctText(
        @Body request: Map<String, String>
    ): AutocorrectResult
    
    @POST("translations/translate")
    suspend fun translateText(
        @Body request: Map<String, String>
    ): TranslationResult
    
    @GET("shortcuts")
    suspend fun getShortcuts(
        @Query("userId") userId: String
    ): List<Shortcut>
    
    @POST("analytics/log")
    suspend fun logTyping(
        @Body request: Map<String, Any>
    ): Map<String, Boolean>
}

object ApiClient {
    private const val BASE_URL = "http://10.0.2.2:5000/api/"
    
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    
    val apiService: ApiService = retrofit.create(ApiService::class.java)
}