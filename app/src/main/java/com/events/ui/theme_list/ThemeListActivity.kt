package com.events.ui.theme_list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.events.databinding.ActivityThemeListBinding
import com.events.utill.PreferencesManager

class ThemeListActivity : AppCompatActivity() {

    private lateinit var preferencesManager: PreferencesManager
    private lateinit var binding: ActivityThemeListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThemeListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferencesManager = PreferencesManager(this)
    }
}