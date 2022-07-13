package com.events.ui.register

import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.events.App
import com.events.databinding.ActivityRegisterBinding
import com.events.utill.Constants
import com.events.utill.PreferencesManager

class RegisterActivity : AppCompatActivity(), RegisterController.View {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var presenter: RegisterPresenter
    private lateinit var progressDialog: ProgressDialog
    private lateinit var preferencesManager: PreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferencesManager = PreferencesManager(this)

        presenter = RegisterPresenter((applicationContext as App).dataManager)
        presenter.attachView(this)

        binding.btnSendBack.setOnClickListener { finish() }

        binding.btnRegisterUser.setOnClickListener {
            presenter.responseRegister(
                binding.editTextUsername.text.toString(),
                binding.editTextPassword.text.toString(),
                binding.editTextPhone.text.toString(),
                binding.editTextLNameFName.text.toString()
            )
        }
    }

    override fun getRegisterUser(status: Boolean, message: String) {
        if (status == true) {
            finish()
        } else {
            binding.textErrorRegister.visibility = View.VISIBLE
            binding.textErrorRegister.text = message
        }
    }

    override fun getDataSuccess(token: String, user_id: String) {
        preferencesManager.putString(Constants.TOKEN, token)
        preferencesManager.putString(Constants.USER_ID, user_id)
        preferencesManager.putString(Constants.USERNAME, binding.editTextUsername.text.toString())
        preferencesManager.putBoolean(Constants.SIGN_UP, true)
    }


    override fun showProgressView(show: Boolean) {
        if (show) progressDialog =
            ProgressDialog.show(this, "", "Подождите пожалуйста, идет загрузка...", false)
        else progressDialog.dismiss()
    }

    override fun noConnection() {
        Toast.makeText(
            this,
            "Проверьте подключение интернета и повторите попытку заново",
            Toast.LENGTH_SHORT
        ).show()

        progressDialog.dismiss()
    }
}