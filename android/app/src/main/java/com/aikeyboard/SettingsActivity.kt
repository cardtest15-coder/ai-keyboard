package com.aikeyboard

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SettingsActivity : AppCompatActivity() {
    
    private lateinit var languageSpinner: Spinner
    private lateinit var themeSpinner: Spinner
    private lateinit var hapticFeedbackSwitch: Switch
    private lateinit var soundEnabledSwitch: Switch
    private lateinit var vibrationIntensityEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var shortcutsRecyclerView: RecyclerView
    private lateinit var shortcutsAdapter: ShortcutsAdapter
    
    private val shortcuts = mutableListOf<Shortcut>()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        
        initViews()
        setupSpinners()
        setupListeners()
        setupShortcutsRecyclerView()
        loadPreferences()
    }
    
    private fun initViews() {
        languageSpinner = findViewById(R.id.language_spinner)
        themeSpinner = findViewById(R.id.theme_spinner)
        hapticFeedbackSwitch = findViewById(R.id.haptic_feedback_switch)
        soundEnabledSwitch = findViewById(R.id.sound_enabled_switch)
        vibrationIntensityEditText = findViewById(R.id.vibration_intensity)
        saveButton = findViewById(R.id.save_settings)
        shortcutsRecyclerView = findViewById(R.id.shortcuts_recycler_view)
    }
    
    private fun setupSpinners() {
        val languages = arrayOf("English", "Русский", "Español", "Deutsch", "Français")
        val languageAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, languages)
        languageSpinner.adapter = languageAdapter
        
        val themes = arrayOf("Dark", "Light", "Auto")
        val themeAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, themes)
        themeSpinner.adapter = themeAdapter
    }
    
    private fun setupListeners() {
        saveButton.setOnClickListener {
            savePreferences()
            finish()
        }
    }
    
    private fun setupShortcutsRecyclerView() {
        shortcutsAdapter = ShortcutsAdapter(shortcuts) { shortcut, action ->
            when (action) {
                "delete" -> {
                    shortcuts.remove(shortcut)
                    shortcutsAdapter.notifyDataSetChanged()
                }
                "edit" -> {
                    // Handle edit
                }
            }
        }
        
        shortcutsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@SettingsActivity)
            adapter = shortcutsAdapter
        }
        
        loadShortcuts()
    }
    
    private fun loadPreferences() {
        val language = PreferenceManager.getLanguage(this)
        val languageIndex = when (language) {
            "en" -> 0
            "ru" -> 1
            "es" -> 2
            "de" -> 3
            "fr" -> 4
            else -> 0
        }
        languageSpinner.setSelection(languageIndex)
        
        val theme = PreferenceManager.getTheme(this)
        val themeIndex = when (theme) {
            "dark" -> 0
            "light" -> 1
            "auto" -> 2
            else -> 0
        }
        themeSpinner.setSelection(themeIndex)
        
        hapticFeedbackSwitch.isChecked = PreferenceManager.isHapticFeedbackEnabled(this)
        soundEnabledSwitch.isChecked = PreferenceManager.isSoundEnabled(this)
        vibrationIntensityEditText.setText(PreferenceManager.getVibrationIntensity(this).toString())
    }
    
    private fun savePreferences() {
        val language = when (languageSpinner.selectedItemPosition) {
            0 -> "en"
            1 -> "ru"
            2 -> "es"
            3 -> "de"
            4 -> "fr"
            else -> "en"
        }
        
        val theme = when (themeSpinner.selectedItemPosition) {
            0 -> "dark"
            1 -> "light"
            2 -> "auto"
            else -> "dark"
        }
        
        val vibrationIntensity = vibrationIntensityEditText.text.toString().toIntOrNull() ?: 50
        
        PreferenceManager.saveLanguage(this, language)
        PreferenceManager.saveTheme(this, theme)
        PreferenceManager.saveHapticFeedbackEnabled(this, hapticFeedbackSwitch.isChecked)
        PreferenceManager.saveSoundEnabled(this, soundEnabledSwitch.isChecked)
        PreferenceManager.saveVibrationIntensity(this, vibrationIntensity)
    }
    
    private fun loadShortcuts() {
        // Load shortcuts from API or local storage
        shortcuts.clear()
        shortcuts.addAll(listOf(
            Shortcut("1", "brb", "be right back"),
            Shortcut("2", "thx", "thank you"),
            Shortcut("3", "omw", "on my way")
        ))
        shortcutsAdapter.notifyDataSetChanged()
    }
}