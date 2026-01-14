package com.aikeyboard

import android.inputmethodservice.InputMethodService
import android.inputmethodservice.KeyboardView
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AIKeyboardService : InputMethodService(), KeyboardView.OnKeyboardActionListener {

    private var keyboardView: KeyboardView? = null
    private var keyboard: AIKeyboardView? = null
    private lateinit var viewModel: KeyboardViewModel
    private var apiService: ApiService
    private var predictionService: PredictionService
    
    override fun onCreate() {
        super.onCreate()
        
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:5000/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        
        apiService = retrofit.create(ApiService::class.java)
        predictionService = PredictionService(apiService)
    }

    override fun onCreateInputView(): View {
        keyboardView = AIKeyboardView(this)
        keyboard = AIKeyboardView(this)
        keyboardView?.setKeyboard(keyboard)
        keyboardView?.setOnKeyboardActionListener(this)
        
        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
            .create(KeyboardViewModel::class.java)
        
        return keyboardView!!
    }

    override fun onStartInputView(editorInfo: EditorInfo, restarting: Boolean) {
        super.onStartInputView(editorInfo, restarting)
        
        val textBeforeCursor = currentInputConnection.getTextBeforeCursor(100, 0)?.toString() ?: ""
        
        lifecycleScope.launch {
            try {
                val predictions = predictionService.getPredictions(textBeforeCursor, "en")
                keyboard?.updatePredictions(predictions)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    override fun onKey(primaryCode: Int, keyCodes: IntArray?) {
        val inputConnection = currentInputConnection
        
        when (primaryCode) {
            KeyEvent.KEYCODE_DELETE -> {
                val selectedText = inputConnection.getSelectedText(0)
                if (TextUtils.isEmpty(selectedText)) {
                    inputConnection.deleteSurroundingText(1, 0)
                } else {
                    inputConnection.commitText("", 1)
                }
            }
            KeyEvent.KEYCODE_SPACE -> {
                inputConnection.commitText(" ", 1)
                
                val textBeforeCursor = inputConnection.getTextBeforeCursor(100, 0)?.toString() ?: ""
                updatePredictions(textBeforeCursor)
            }
            KeyEvent.KEYCODE_ENTER -> {
                inputConnection.sendKeyEvent(
                    KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER)
                )
            }
            else -> {
                val keyChar = primaryCode.toChar()
                inputConnection.commitText(keyChar.toString(), 1)
                
                val textBeforeCursor = inputConnection.getTextBeforeCursor(100, 0)?.toString() ?: ""
                updatePredictions(textBeforeCursor)
            }
        }
    }

    private suspend fun updatePredictions(text: String) {
        try {
            val predictions = predictionService.getPredictions(text, "en")
            keyboard?.updatePredictions(predictions)
        } catch (e: Exception) {
            // Handle error
        }
    }

    override fun onPress(primaryCode: Int) {
        if (primaryCode != KeyEvent.KEYCODE_SPACE && primaryCode != KeyEvent.KEYCODE_DELETE) {
            vibrate(50)
        }
    }

    override fun onRelease(primaryCode: Int) {}

    override fun onText(text: CharSequence?) {
        currentInputConnection.commitText(text, 1)
    }

    override fun swipeLeft() {
        currentInputConnection.commitText(" ", 1)
    }

    override fun swipeRight() {
        currentInputConnection.deleteSurroundingText(1, 0)
    }

    override fun swipeDown() {
        currentInputConnection.sendKeyEvent(
            KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER)
        )
    }

    override fun swipeUp() {
        val selectedText = currentInputConnection.getSelectedText(0)
        if (selectedText != null) {
            val capitalized = selectedText.toString().uppercase()
            currentInputConnection.commitText(capitalized, 1)
        }
    }
}