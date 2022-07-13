package com.events.ui.login

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.events.App
import com.events.MainActivity
import com.events.databinding.FragmentLoginUserBinding
import com.events.ui.profile.ProfileFragment
import com.events.ui.register.RegisterActivity
import com.events.utill.Constants
import com.events.utill.PreferencesManager

class LoginUserFragment : Fragment(), LoginController.View {

    private lateinit var binding: FragmentLoginUserBinding
    private lateinit var progressDialog: ProgressDialog
    private lateinit var presenter: LoginPresenter
    private lateinit var preferencesManager: PreferencesManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginUserBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferencesManager = PreferencesManager(requireContext())

        presenter = LoginPresenter((view.context.applicationContext as App).dataManager)
        presenter.attachView(this)

        binding.registerSend.setOnClickListener {
            val intent = Intent(requireContext(), RegisterActivity::class.java)
            it.context.startActivity(intent)
        }
        binding.btnLoginUser.setOnClickListener {
            presenter.responseLoginSuccessFully(
                binding.editTextUsername.text.toString(),
                binding.editTextPassword.text.toString()
            )
        }
    }

    override fun getLoginSuccessFully(status: Boolean, message: String) {
        if (status == false) {
            binding.textErrorLogin.visibility = View.VISIBLE
            binding.textErrorLogin.text = message
        } else {
            binding.textErrorLogin.visibility = View.GONE
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            (requireContext() as MainActivity).setCurrentFragment(ProfileFragment())
        }
    }

    override fun getDataSuccess(user_id: String, token: String) {
        preferencesManager.putString(Constants.USER_ID, user_id)
        preferencesManager.putString(Constants.TOKEN, token)
        preferencesManager.putString(Constants.USERNAME, binding.editTextUsername.text.toString())
        preferencesManager.putBoolean(Constants.SIGN_UP, true)
    }

    override fun showProgressView(show: Boolean) {
        if (show) {
            binding.btnLoginUser.isEnabled == false
            progressDialog = ProgressDialog.show(
                requireContext(),
                "",
                "Подождите пожалуйста, идет загрузка...",
                false
            )
        } else {
            binding.btnLoginUser.isEnabled == true
            progressDialog.dismiss()
        }

    }

    override fun connectionView() {
        progressDialog.dismiss()
        Toast.makeText(requireContext(), "Проверьте подключение интернета.", Toast.LENGTH_SHORT)
            .show()
    }

    override fun onResume() {
        if (preferencesManager.getBoolean(Constants.SIGN_UP)) {
            (requireContext() as MainActivity).setCurrentFragment(ProfileFragment())
        }
        super.onResume()
    }
}