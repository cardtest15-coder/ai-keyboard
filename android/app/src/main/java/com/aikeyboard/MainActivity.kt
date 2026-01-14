package com.aikeyboard

import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    
    private lateinit var keyboardStatusText: TextView
    private lateinit var enableKeyboardButton: Button
    private lateinit var openSettingsButton: Button
    private lateinit var predictiveSwitch: Switch
    private lateinit var autocorrectSwitch: Switch
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        initViews()
        setupListeners()
        loadPreferences()
    }
    
    private fun initViews() {
        keyboardStatusText = findViewById(R.id.keyboard_status)
        enableKeyboardButton = findViewById(R.id.enable_keyboard)
        openSettingsButton = findViewById(R.id.open_settings)
        predictiveSwitch = findViewById(R.id.predictive_text_switch)
        autocorrectSwitch = findViewById(R.id.autocorrect_switch)
    }
    
    private fun setupListeners() {
        enableKeyboardButton.setOnClickListener {
            openInputMethodSettings()
        }
        
        openSettingsButton.setOnClickListener {
            openSettings()
        }
        
        predictiveSwitch.setOnCheckedChangeListener { _, isChecked ->
            PreferenceManager.savePredictiveTextEnabled(this, isChecked)
        }
        
        autocorrectSwitch.setOnCheckedChangeListener { _, isChecked ->
            PreferenceManager.saveAutocorrectEnabled(this, isChecked)
        }
    }
    
    private fun loadPreferences() {
        predictiveSwitch.isChecked = PreferenceManager.isPredictiveTextEnabled(this)
        autocorrectSwitch.isChecked = PreferenceManager.isAutocorrectEnabled(this)
    }
    
    private fun openInputMethodSettings() {
        val intent = android.provider.Settings.ACTION_INPUT_METHOD_SETTINGS
        startActivity(intent)
    }
    
    private fun openSettings() {
        val intent = android.content.Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }
}