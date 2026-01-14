package com.aikeyboard

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PredictionService(private val apiService: ApiService) {
    
    suspend fun getPredictions(text: String, language: String = "en"): List<String> {
        return try {
            val response = apiService.getPredictions(text, language)
            response.map { it.text }
        } catch (e: Exception) {
            // Fallback to local predictions
            getLocalPredictions(text, language)
        }
    }
    
    private fun getLocalPredictions(text: String, language: String): List<String> {
        val commonPhrases = mapOf(
            "en" to listOf("hello", "thank you", "please", "how are you", "I think"),
            "ru" to listOf("привет", "спасибо", "пожалуйста", "как дела", "я думаю"),
            "es" to listOf("hola", "gracias", "por favor", "cómo estás", "creo que"),
            "de" to listOf("hallo", "danke", "bitte", "wie geht es", "ich denke")
        )
        
        val phrases = commonPhrases[language] ?: commonPhrases["en"]!!
        
        return phrases.filter { phrase ->
            phrase.lowercase().startsWith(text.lowercase()) || text.length < 2
        }.take(5)
    }
    
    suspend fun autocorrect(text: String, language: String = "en"): AutocorrectResult {
        return try {
            apiService.correctText(mapOf("text" to text, "language" to language))
        } catch (e: Exception) {
            AutocorrectResult(text, text, 0)
        }
    }
    
    suspend fun translateText(text: String, sourceLang: String, targetLang: String): TranslationResult {
        return try {
            apiService.translateText(mapOf(
                "text" to text,
                "sourceLang" to sourceLang,
                "targetLang" to targetLang
            ))
        } catch (e: Exception) {
            TranslationResult(text, text, sourceLang, targetLang)
        }
    }
}