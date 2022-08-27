package com.events.ui.savedevent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.events.databinding.ActivitySavedEventBinding

class SavedEventActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySavedEventBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySavedEventBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}