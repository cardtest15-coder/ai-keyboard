package com.aikeyboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class KeyboardViewModel : ViewModel() {
    
    private val _predictions = MutableStateFlow<List<String>>(emptyList())
    val predictions: StateFlow<List<String>> = _predictions
    
    private val _text = MutableStateFlow("")
    val text: StateFlow<String> = _text
    
    private val _isAutocorrectEnabled = MutableStateFlow(true)
    val isAutocorrectEnabled: StateFlow<Boolean> = _isAutocorrectEnabled
    
    fun updatePredictions(newPredictions: List<String>) {
        _predictions.value = newPredictions
    }
    
    fun updateText(newText: String) {
        _text.value = newText
    }
    
    fun toggleAutocorrect() {
        _isAutocorrectEnabled.value = !_isAutocorrectEnabled.value
    }
    
    fun clearText() {
        _text.value = ""
        _predictions.value = emptyList()
    }
    
    fun getWordSuggestions(partialWord: String): List<String> {
        val commonWords = listOf(
            "hello", "world", "keyboard", "android", "mobile", 
            "phone", "text", "message", "app", "software",
            "development", "coding", "programming", "computer"
        )
        
        return commonWords.filter { 
            it.startsWith(partialWord.lowercase()) 
        }.take(5)
    }
}