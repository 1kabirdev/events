package com.events.ui.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.events.databinding.ActivityEditAccountBinding

class EditAccountActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditAccountBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}