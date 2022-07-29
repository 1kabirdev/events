package com.events.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.events.App
import com.events.MainActivity
import com.events.databinding.FragmentProfileBinding
import com.events.model.profile.ResponseInfoProfile
import com.events.ui.bottom_sheet.InfoProfileBottomSheet
import com.events.ui.comments.CommentsActivity
import com.events.ui.edit_profile.EditProfileActivity
import com.events.ui.login.LoginUserFragment
import com.events.ui.profile.adapter.AdapterMyEvents
import com.events.utill.Constants
import com.events.utill.LinearEventEndlessScrollEventListener
import com.events.utill.PreferencesManager

class ProfileFragment : Fragment(), ProfileController.View, InfoProfileBottomSheet.OnClickListener,
    AdapterMyEvents.OnClickListener {

    private lateinit var preferencesManager: PreferencesManager
    private lateinit var binding: FragmentProfileBinding
    private lateinit var presenter: ProfilePresenter
    private lateinit var adapterMyEvents: AdapterMyEvents
    private lateinit var endlessScrollEventListener: LinearEventEndlessScrollEventListener
    private lateinit var layoutManager: LinearLayoutManager
    private var errorFailed = false
    private var isLoading = false
    private var isLastPage = false
    private val PAGE_START = 1
    private var currentPage: Int = PAGE_START
    var user: ResponseInfoProfile.ProfileData? = null

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
            PAGE_START
        )
        with(binding) {

            toolbarProfile.btnAddEvents.setOnClickListener {
                (requireActivity() as MainActivity).getOnCreateEvents()
            }

            btnReplyProfile.setOnClickListener {
                presenter.responseLoadDataProfile(
                    preferencesManager.getString(Constants.USER_ID),
                    PAGE_START
                )
            }

            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            recyclerViewEvents.layoutManager = layoutManager
            setEndlessScrollEventListener()
            recyclerViewEvents.addOnScrollListener(endlessScrollEventListener)
        }
    }


    private fun setEndlessScrollEventListener() {
        endlessScrollEventListener =
            object : LinearEventEndlessScrollEventListener(
                layoutManager
            ) {
                override fun onLoadMore(recyclerView: RecyclerView?) {
                    recyclerView.apply {
                        isLoading = true
                        if (currentPage != 0) {
                            presenter.responseLoadDataPage(
                                preferencesManager.getString(Constants.USER_ID),
                                currentPage
                            )
                        }
                    }
                }
            }
    }

    fun getLogoutAccount() {
        preferencesManager.clear()
        (requireContext() as MainActivity).setCurrentFragment(LoginUserFragment())
    }

    override fun getLoadData(
        profileData: ResponseInfoProfile.ProfileData,
        infoPage: ResponseInfoProfile.InfoPage,
        eventsList: ArrayList<ResponseInfoProfile.ResponseEvents>
    ) {

        user = ResponseInfoProfile.ProfileData(
            preferencesManager.getString(Constants.USER_ID),
            profileData.username,
            profileData.avatar,
            profileData.phone,
            profileData.last_name,
            profileData.about,
            profileData.create_data
        )
        currentPage = infoPage.next_page
        adapterMyEvents = AdapterMyEvents(eventsList, this@ProfileFragment)
        adapterMyEvents.profile(profileData)
        adapterMyEvents.infoCountEvent(infoPage.count_event)
        with(binding) {

            recyclerViewEvents.adapter = adapterMyEvents

            /**
             * menu toolbar
             */
            toolbarProfile.btnMoreProfile.setOnClickListener {
                (requireActivity() as MainActivity).createDialogFragment(
                    InfoProfileBottomSheet(this@ProfileFragment, this@ProfileFragment)
                )
            }
        }
        if (infoPage.next_page != 0)
            if (currentPage <= infoPage.count_page) adapterMyEvents.addLoadingFooter() else isLastPage =
                true
    }

    override fun getLoadDataPage(
        infoPage: ResponseInfoProfile.InfoPage,
        eventsList: ArrayList<ResponseInfoProfile.ResponseEvents>
    ) {
        currentPage = infoPage.next_page
        adapterMyEvents.addAll(eventsList)
        isLoading = false
        adapterMyEvents.removeLoadingFooter()
        if (infoPage.next_page != 0)
            if (currentPage != infoPage.count_page) adapterMyEvents.addLoadingFooter() else isLastPage =
                true
    }

    override fun progressBar(show: Boolean) {
        if (show) {
            binding.constraintConnection.visibility = View.GONE
            binding.progressBarEventView.visibility = View.VISIBLE
        } else {
            binding.constraintConnection.visibility = View.GONE
            binding.progressBarEventView.visibility = View.GONE
        }
    }

    override fun noConnection() {
        binding.progressBarEventView.visibility = View.GONE
        binding.constraintConnection.visibility = View.VISIBLE
    }

    override fun noConnectPage() {
        errorFailed = true
        adapterMyEvents.removeLoadingFooter()
        adapterMyEvents.showRetry(true)
    }

    override fun onClickEdit() {
        val intent = Intent(requireContext(), EditProfileActivity::class.java)
        intent.putExtra("AVATAR", user!!.avatar)
        intent.putExtra("USERNAME", user!!.username)
        intent.putExtra("LASTNAME", user!!.last_name)
        intent.putExtra("ABOUT", user!!.about)
        startActivity(intent)
    }

    override fun onClickUserEdit(profileData: ResponseInfoProfile.ProfileData) {
        val intent = Intent(requireContext(), EditProfileActivity::class.java)
        intent.putExtra("AVATAR", profileData.avatar)
        intent.putExtra("USERNAME", profileData.username)
        intent.putExtra("LASTNAME", profileData.last_name)
        intent.putExtra("ABOUT", profileData.about)
        startActivity(intent)
    }

    override fun OnClickReply() {
        errorFailed = false
        adapterMyEvents.showRetry(false)
        adapterMyEvents.addLoadingFooter()
        presenter.responseLoadDataPage(
            preferencesManager.getString(Constants.USER_ID),
            currentPage
        )
    }

    override fun onClickDiscuss(
        id: Int,
        image: String,
        name: String,
        theme: String,
        date: String,
        time: String
    ) {
        val intent = Intent(requireContext(), CommentsActivity::class.java)
        intent.putExtra("EVENT_ID", id.toString())
        intent.putExtra("EVENT_IMAGE", image)
        intent.putExtra("EVENT_NAME", name)
        intent.putExtra("EVENT_THEME", theme)
        intent.putExtra("EVENT_DATE", "$date в $time")
        startActivity(intent)
    }

}