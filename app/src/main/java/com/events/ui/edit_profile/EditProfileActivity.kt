package com.events.ui.edit_profile

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import com.events.App
import com.events.R
import com.events.databinding.ActivityEditAccountBinding
import com.events.model.profile.UpdateAvatar
import com.events.utill.Constants
import com.events.utill.PreferencesManager
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import java.io.ByteArrayOutputStream
import java.io.InputStream

class EditProfileActivity : AppCompatActivity(), EditProfileController.View {

    private val INTENT_REQUEST_CODE = 100
    private var inp: InputStream? = null
    private lateinit var progressBar: ProgressDialog
    private var avatar: String? = null
    private var username: String? = null
    private var lastName: String? = null
    private var about: String? = null

    private lateinit var presenter: EditProfilePresenter
    private lateinit var preferencesManager: PreferencesManager

    private lateinit var binding: ActivityEditAccountBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferencesManager = PreferencesManager(this)

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
            clickUpdateAvatar.setOnClickListener { intentImageGallery() }
            btnSaveEdit.setOnClickListener {
            }
        }
    }


    @Deprecated("Deprecated in Java")
    @SuppressLint("Recycle")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == INTENT_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                val selectedImage: Uri = data!!.data!!
                val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                val cursor: Cursor =
                    contentResolver.query(
                        selectedImage,
                        filePathColumn,
                        null,
                        null,
                        null
                    )!!
                cursor.moveToFirst()
                val columnIndex: Int = cursor.getColumnIndexOrThrow(filePathColumn[0])
                val picturePath = cursor.getString(columnIndex)
                if (picturePath != "") {
                    inp = contentResolver.openInputStream(selectedImage)!!
                    binding.avatarEditImageView.setImageURI(selectedImage)
                    presenter.responseUpdateAvatar(
                        preferencesManager.getString(Constants.USER_ID).toInt(),
                        getBytes(inp!!)!!
                    )
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun getBytes(inp: InputStream): ByteArray? {
        val byteBuff = ByteArrayOutputStream()
        val buffSize = 2048
        val buff = ByteArray(buffSize)
        var len = 0
        while (inp.read(buff).also { len = it } != -1) {
            byteBuff.write(buff, 0, len)
        }
        return byteBuff.toByteArray()
    }


    private fun intentImageGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        try {
            startActivityForResult(intent, INTENT_REQUEST_CODE)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
        }
    }

    override fun updateAvatar(updateAvatar: UpdateAvatar) {
        if (updateAvatar.status)
            Snackbar.make(binding.nested, "Фото профиля обновлено", Snackbar.LENGTH_LONG)
        else Snackbar.make(binding.nested, "Ошибка.", Snackbar.LENGTH_LONG)
    }

    override fun progressAvatar(show: Boolean) {
        if (show)
            progressBar =
                ProgressDialog.show(this, "Загрузка", "Подождите пожалуйста...", false)
        else progressBar.dismiss()
    }

    override fun errorAvatar() {
        progressBar.dismiss()
        Toast.makeText(this, "Проверьте подключение интернета.", Toast.LENGTH_SHORT)
            .show()
    }
}