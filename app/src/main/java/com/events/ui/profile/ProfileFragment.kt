package com.events.ui.profile

import android.annotation.SuppressLint
import android.content.Intent
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
import com.events.model.profile.InfoPage
import com.events.model.profile.ProfileData
import com.events.model.profile.ResponseEvents
import com.events.ui.bottom_sheet.InfoProfileBottomSheet
import com.events.ui.edit_profile.EditProfileActivity
import com.events.ui.login.LoginUserFragment
import com.events.utill.Constants
import com.events.utill.PreferencesManager
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment(), ProfileController.View, InfoProfileBottomSheet.OnClickListener {

    private lateinit var preferencesManager: PreferencesManager
    private lateinit var binding: FragmentProfileBinding
    private lateinit var presenter: ProfilePresenter
    private lateinit var adapterMyEvents: AdapterMyEvents
    private val START_PAGE: Int = 1
    private var current_page = START_PAGE + 1
    var user: ProfileData? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferencesManager = PreferencesManager(requireContext())

        presenter = ProfilePresenter((view.context.applicationContext as App).dataManager)
        presenter.attachView(this)
        presenter.responseLoadDataProfile(
            preferencesManager.getString(Constants.USER_ID),
            START_PAGE
        )
        with(binding) {
            btnClickEditProfile.setOnClickListener {
                val intent = Intent(requireContext(), EditProfileActivity::class.java)
                intent.putExtra("AVATAR", user!!.avatar)
                intent.putExtra("USERNAME", user!!.username)
                intent.putExtra("LASTNAME", user!!.last_name)
                intent.putExtra("ABOUT", user!!.about)
                startActivity(intent)
            }

            btnAddEvents.setOnClickListener {
                (requireActivity() as MainActivity).getOnCreateEvents()
            }

            btnMoreProfile.setOnClickListener {
                (requireActivity() as MainActivity).createDialogFragment(
                    InfoProfileBottomSheet(this@ProfileFragment, this@ProfileFragment)
                )
            }
        }
    }

    fun getLogoutAccount() {
        preferencesManager.clear()
        (requireContext() as MainActivity).setCurrentFragment(LoginUserFragment())
    }

    @SuppressLint("SetTextI18n")
    override fun getLoadData(
        profileData: ProfileData,
        infoPage: InfoPage,
        eventsList: ArrayList<ResponseEvents>
    ) {
        Picasso.get().load(profileData.avatar).into(binding.avatarProfile)
        binding.usernameProfile.text = "@${profileData.username}"
        binding.lastNameProfile.text = profileData.last_name
        binding.aboutProfile.text = profileData.about

        user = ProfileData(
            preferencesManager.getString(Constants.USER_ID),
            profileData.username,
            profileData.avatar,
            profileData.phone,
            profileData.last_name,
            profileData.about,
            profileData.create_data
        )

        adapterMyEvents = AdapterMyEvents(eventsList)
        if (eventsList.size != 0) {
            binding.titleEvents.text = "Мероприятия (${eventsList.size})"
            binding.recyclerViewEvents.adapter = adapterMyEvents
        } else {
            binding.constraintRecyclerView.visibility = View.GONE
            binding.constraintNotEvents.visibility = View.VISIBLE
        }
    }

    override fun progressBar(show: Boolean) {
        if (show) {
            binding.nestedScrollViewProfile.visibility = View.GONE
            binding.progressBarEventView.visibility = View.VISIBLE
        } else {
            binding.nestedScrollViewProfile.visibility = View.VISIBLE
            binding.progressBarEventView.visibility = View.GONE
        }
    }

    override fun noConnection() {
        binding.nestedScrollViewProfile.visibility = View.GONE
        binding.progressBarEventView.visibility = View.GONE
        binding.noConnectionView.visibility = View.VISIBLE
        Toast.makeText(
            requireContext(),
            "Проверьте подключение интернета.",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onClickEdit() {
        val intent = Intent(requireContext(), EditProfileActivity::class.java)
        intent.putExtra("AVATAR", user!!.avatar)
        intent.putExtra("USERNAME", user!!.username)
        intent.putExtra("LASTNAME", user!!.last_name)
        intent.putExtra("ABOUT", user!!.about)
        startActivity(intent)
    }

}