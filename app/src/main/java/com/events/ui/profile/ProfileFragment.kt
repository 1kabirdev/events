package com.events.ui.profile

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.events.App
import com.events.MainActivity
import com.events.adapter.AdapterMyEvents
import com.events.databinding.FragmentProfileBinding
import com.events.model.my_events.MyEventsList
import com.events.ui.bottom_sheet.InfoProfileBottomSheet
import com.events.ui.login.LoginUserFragment
import com.events.utill.SharedPreferences
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment(), ProfileController.View {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var presenter: ProfilePresenter
    private lateinit var adapterMyEvents: AdapterMyEvents
    private val limitEvent: Int = 10

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = ProfilePresenter((view.context.applicationContext as App).dataManager)
        presenter.attachView(this)

        binding.btnAddEvents.setOnClickListener {
            (requireActivity() as MainActivity).getOnCreateEvents()
        }

        binding.btnMoreProfile.setOnClickListener {
            (requireActivity() as MainActivity).createDialogFragment(
                InfoProfileBottomSheet(this)
            )
        }

        presenter.responseLoadDataProfile(SharedPreferences.loadToken(requireContext()).toString())
        presenter.responseLoadMyEvents(
            SharedPreferences.loadIdUser(requireContext()).toString(),
            limitEvent.toString()
        )
    }

    fun getLogoutAccount() {
        SharedPreferences.saveIdUser("", requireContext())
        SharedPreferences.saveToken("", requireContext())
        (requireContext() as MainActivity).setCurrentFragment(LoginUserFragment())
    }

    @SuppressLint("SetTextI18n")
    override fun getLoadData(
        username: String,
        avatar: String,
        phone: String,
        last_name: String,
        create_data: String,
        about: String
    ) {
        Picasso.get().load(avatar).into(binding.avatarProfile)
        binding.usernameProfile.text = "@$username"
        binding.lastNameProfile.text = last_name
        binding.aboutProfile.text = about
        binding.aboutProfile.setOnClickListener {
        }
    }

    @SuppressLint("SetTextI18n")
    override fun getLoadMyEvents(eventsList: ArrayList<MyEventsList>) {
        adapterMyEvents = AdapterMyEvents(eventsList)
        if (eventsList.size != 0) {
            binding.titleEvents.text = "Мероприятия (${eventsList.size})"
            binding.recyclerViewEvents.adapter = adapterMyEvents
        } else {
            binding.constraintRecyclerView.visibility = View.GONE
            binding.constraintNotEvents.visibility = View.VISIBLE
        }
    }

    override fun showProgressViewEvents() {
        binding.progressBarEventView.visibility = View.VISIBLE
    }

    override fun hideProgressViewEvents() {
        binding.progressBarEventView.visibility = View.GONE
    }

    override fun showProgressView() {
        binding.nestedScrollViewProfile.visibility = View.GONE
        binding.progressBarProfile.visibility = View.VISIBLE
    }

    override fun hideProgressView() {
        binding.nestedScrollViewProfile.visibility = View.VISIBLE
        binding.progressBarProfile.visibility = View.GONE
    }

    override fun noConnection() {
        binding.nestedScrollViewProfile.visibility = View.GONE
        binding.progressBarProfile.visibility = View.GONE
        binding.noConnectionView.visibility = View.VISIBLE
        Toast.makeText(
            requireContext(),
            "Проверьте подключение интернета.",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onResume() {
        presenter.responseLoadMyEvents(
            SharedPreferences.loadIdUser(requireContext()).toString(),
            limitEvent.toString()
        )
        super.onResume()
    }
}