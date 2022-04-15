package com.events.ui.register

import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.events.App
import com.events.R
import com.events.databinding.ActivityRegisterBinding
import com.events.utill.SharedPreferences
import com.google.android.material.textfield.TextInputEditText

class RegisterActivity : AppCompatActivity(), RegisterController.View {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var presenter: RegisterPresenter
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = RegisterPresenter((applicationContext as App).dataManager)
        presenter.attachView(this)
        binding.btnSendBack.setOnClickListener {
            finish()
        }

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

    override fun getTokenUser(token: String) {
        SharedPreferences.saveToken(token, this)
    }

    override fun getUserId(user_id: String) {
        SharedPreferences.saveIdUser(user_id, this)
    }

    override fun showProgressView() {
        progressDialog =
            ProgressDialog.show(this, "", "Подождите пожалуйста, идет загрузка...", false)
    }

    override fun hideProgressView() {
        progressDialog.dismiss()
    }

    override fun noConnection() {
        Toast.makeText(
            this,
            "Проверьте подключение интернета и повторите попытку заново",
            Toast.LENGTH_SHORT
        ).show()
    }
}