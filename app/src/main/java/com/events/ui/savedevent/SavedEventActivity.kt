package com.events.ui.savedevent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.events.App
import com.events.databinding.ActivitySavedEventBinding
import com.events.room.dao.DaoRoom
import com.events.utill.Constants
import com.events.utill.PreferencesManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SavedEventActivity : AppCompatActivity() {

    private lateinit var dao: DaoRoom
    private lateinit var preferencesManager: PreferencesManager
    private lateinit var binding: ActivitySavedEventBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySavedEventBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preferencesManager = PreferencesManager(this)
        dao = (application as App).getDatabase().getSavedEventDao()

        lifecycleScope.launch(Dispatchers.IO) {
            val list = dao.getSavedEvent(
                preferencesManager.getString(Constants.USER_ID).toInt()
            ) as ArrayList
            withContext(Dispatchers.Main) {
                for (i in list) {
                    Log.d("LIST", i.name_e)
                    Toast.makeText(this@SavedEventActivity, "${i.name_e}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
}