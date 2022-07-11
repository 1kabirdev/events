package com.events.ui.setting_password

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.events.databinding.ActivitySettingPasswordBinding

class SettingPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(binding) {
            btnBack.setOnClickListener { finish() }
        }
    }
}