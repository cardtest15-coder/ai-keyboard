package com.aikeyboard

import android.content.Context
import android.content.SharedPreferences

object PreferenceManager {
    private const val PREFS_NAME = "ai_keyboard_prefs"
    private const val KEY_LANGUAGE = "language"
    private const val KEY_THEME = "theme"
    private const val KEY_HAPTIC_FEEDBACK = "haptic_feedback"
    private const val KEY_SOUND_ENABLED = "sound_enabled"
    private const val KEY_VIBRATION_INTENSITY = "vibration_intensity"
    private const val KEY_PREDICTIVE_TEXT = "predictive_text"
    private const val KEY_AUTOCORRECT = "autocorrect"
    
    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }
    
    fun saveLanguage(context: Context, language: String) {
        getPrefs(context).edit().putString(KEY_LANGUAGE, language).apply()
    }
    
    fun getLanguage(context: Context): String {
        return getPrefs(context).getString(KEY_LANGUAGE, "en") ?: "en"
    }
    
    fun saveTheme(context: Context, theme: String) {
        getPrefs(context).edit().putString(KEY_THEME, theme).apply()
    }
    
    fun getTheme(context: Context): String {
        return getPrefs(context).getString(KEY_THEME, "dark") ?: "dark"
    }
    
    fun saveHapticFeedbackEnabled(context: Context, enabled: Boolean) {
        getPrefs(context).edit().putBoolean(KEY_HAPTIC_FEEDBACK, enabled).apply()
    }
    
    fun isHapticFeedbackEnabled(context: Context): Boolean {
        return getPrefs(context).getBoolean(KEY_HAPTIC_FEEDBACK, true)
    }
    
    fun saveSoundEnabled(context: Context, enabled: Boolean) {
        getPrefs(context).edit().putBoolean(KEY_SOUND_ENABLED, enabled).apply()
    }
    
    fun isSoundEnabled(context: Context): Boolean {
        return getPrefs(context).getBoolean(KEY_SOUND_ENABLED, false)
    }
    
    fun saveVibrationIntensity(context: Context, intensity: Int) {
        getPrefs(context).edit().putInt(KEY_VIBRATION_INTENSITY, intensity).apply()
    }
    
    fun getVibrationIntensity(context: Context): Int {
        return getPrefs(context).getInt(KEY_VIBRATION_INTENSITY, 50)
    }
    
    fun savePredictiveTextEnabled(context: Context, enabled: Boolean) {
        getPrefs(context).edit().putBoolean(KEY_PREDICTIVE_TEXT, enabled).apply()
    }
    
    fun isPredictiveTextEnabled(context: Context): Boolean {
        return getPrefs(context).getBoolean(KEY_PREDICTIVE_TEXT, true)
    }
    
    fun saveAutocorrectEnabled(context: Context, enabled: Boolean) {
        getPrefs(context).edit().putBoolean(KEY_AUTOCORRECT, enabled).apply()
    }
    
    fun isAutocorrectEnabled(context: Context): Boolean {
        return getPrefs(context).getBoolean(KEY_AUTOCORRECT, true)
    }
}