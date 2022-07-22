package com.events.ui.edit_profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.events.App
import com.events.R
import com.events.databinding.ActivityEditAccountBinding
import com.events.model.profile.UpdateAvatar
import com.squareup.picasso.Picasso

class EditProfileActivity : AppCompatActivity(), EditProfileController.View {

    private var avatar: String? = null
    private var username: String? = null
    private var lastName: String? = null
    private var about: String? = null

    private lateinit var presenter: EditProfilePresenter

    private lateinit var binding: ActivityEditAccountBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val args = intent.extras
        avatar = args?.get("AVATAR").toString() ?: "default"
        username = args?.get("USERNAME").toString() ?: "default"
        lastName = args?.get("LASTNAME").toString() ?: "default"
        about = args?.get("ABOUT").toString() ?: "default"

        presenter = EditProfilePresenter((applicationContext as App).dataManager)
        presenter.attachView(this)

        with(binding) {
            btnBack.setOnClickListener { finish() }
            Picasso.get().load(avatar).into(avatarEditImageView)
            editTextUsername.setText(username)
            editTextLastName.setText(lastName)
            editTextAbout.setText(about)
            btnSaveEdit.setOnClickListener {
            }
        }
    }

    override fun updateAvatar(updateAvatar: UpdateAvatar) {

    }

    override fun progressAvatar(show: Boolean) {

    }

    override fun errorAvatar() {

    }
}