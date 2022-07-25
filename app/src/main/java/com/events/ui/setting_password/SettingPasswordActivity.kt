package com.events.ui.setting_password

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.events.App
import com.events.databinding.ActivitySettingPasswordBinding
import com.events.model.profile.UpdatePassword
import com.events.ui.setting_password.updateImpl.UpdatePasswordContract
import com.events.ui.setting_password.updateImpl.UpdatePasswordPresenterImpl
import com.events.utill.Constants
import com.events.utill.PreferencesManager
import com.google.android.material.snackbar.Snackbar

class SettingPasswordActivity : AppCompatActivity(), UpdatePasswordContract.View {

    private lateinit var binding: ActivitySettingPasswordBinding
    private lateinit var presenterImpl: UpdatePasswordPresenterImpl
    private lateinit var preferencesManager: PreferencesManager
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferencesManager = PreferencesManager(this)

        presenterImpl = UpdatePasswordPresenterImpl((applicationContext as App).dataManager)
        presenterImpl.attachView(this)

        with(binding) {
            btnBack.setOnClickListener { finish() }
            btnEditPassword.setOnClickListener {
                presenterImpl.responseUpdatePassword(
                    preferencesManager.getString(Constants.USER_ID).toInt(),
                    editTextOldPassword.text.toString(),
                    editTextNewPassword.text.toString(),
                    editTextConfPassword.text.toString()
                )
            }
        }
    }

    override fun updatePassword(updatePassword: UpdatePassword) {
        if (updatePassword.status) {
            Snackbar.make(binding.nested, "Ваш пароль успешно изменен", Snackbar.LENGTH_LONG).show()
        }
    }

    override fun progress(show: Boolean) {
        if (show)
            progressDialog = ProgressDialog.show(this, "Загрузка", "Подождите пожалуйста...",  false)
        else progressDialog.dismiss()
    }

    override fun isEmptyPassword(message: String) {
        Snackbar.make(
            binding.nested,
            message,
            Snackbar.LENGTH_LONG
        ).show()
    }

    override fun error() {
        progressDialog.dismiss()
        Snackbar.make(
            binding.nested,
            "Проверьте подключение к Интнерту и повторите попытку.",
            Snackbar.LENGTH_LONG
        ).show()

    }
}