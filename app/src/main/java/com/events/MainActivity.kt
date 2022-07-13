package com.events

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.events.databinding.ActivityMainBinding
import com.events.ui.create_events.CreateEventFragment
import com.events.ui.home.HomeEventsFragment
import com.events.ui.login.LoginUserFragment
import com.events.utill.Constants
import com.events.utill.PreferencesManager
import com.google.android.material.bottomnavigation.BottomNavigationView

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var preferencesManager: PreferencesManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preferencesManager = PreferencesManager(this)
        binding.bottomNavView.setOnNavigationItemSelectedListener(this)
        setCurrentFragment(HomeEventsFragment())
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_home -> {
                setCurrentFragment(HomeEventsFragment())
                return true
            }

            R.id.navigation_create_event -> {
                if (preferencesManager.getBoolean(Constants.SIGN_UP)) {
                    setCurrentFragment(CreateEventFragment())
                } else {
                    alertDialogNotAccount()
                    return false
                }
                return true
            }

            R.id.navigation_profile -> {
                setCurrentFragment(LoginUserFragment())
                return true
            }
        }
        return false
    }

    fun getOnCreateEvents() {
        binding.bottomNavView.selectedItemId = R.id.navigation_create_event
    }

    fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            try {
                replace(R.id.nav_host_fragment, fragment)
                commit()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    override fun onBackPressed() {
        when (binding.bottomNavView.selectedItemId) {
            R.id.navigation_home -> {
                super.onBackPressed()
                finish()
            }
            R.id.navigation_create_event -> {
                binding.bottomNavView.selectedItemId = R.id.navigation_home
            }
            R.id.navigation_profile -> {
                if (preferencesManager.getBoolean(Constants.SIGN_UP)) {
                    binding.bottomNavView.selectedItemId = R.id.navigation_home
                } else {
                    binding.bottomNavView.selectedItemId = R.id.navigation_home
                }
            }
            else -> {
                binding.bottomNavView.selectedItemId = R.id.navigation_home
            }
        }
    }

    private fun alertDialogNotAccount(): AlertDialog.Builder {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this@MainActivity)
        builder.setTitle("Это действие требует авторизации!")
            .setMessage("Пожалуйста, войдите в свой аккаунт, чтобы выполнить это действие, или зарегистрируйтесь")
            .setCancelable(false)
            .setNegativeButton("Войти") { dialog, _ ->
                binding.bottomNavView.selectedItemId = R.id.navigation_profile
                dialog.cancel()
            }
        val alert: AlertDialog = builder.create()
        alert.show()
        return builder
    }

    fun createDialogFragment(fragment: DialogFragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        fragment.show(fragmentManager, "DialogAction")
    }
}