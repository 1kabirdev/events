package com.events.ui.bottom_sheet

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.events.databinding.FragmentInfoProfileBottomSheetBinding
import com.events.ui.profile.ProfileFragment
import com.events.ui.setting_password.SettingPasswordActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class InfoProfileBottomSheet(
    private var fragment: ProfileFragment,
    private var listener: OnClickListener
) : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentInfoProfileBottomSheetBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInfoProfileBottomSheetBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            logautAccount.setOnClickListener {
                fragment.getLogoutAccount()
                dismiss()
            }
            editProfile.setOnClickListener {
                listener.onClickEdit()
            }
            settingPassword.setOnClickListener {
                startActivity(Intent(requireContext(), SettingPasswordActivity::class.java))
            }
        }

    }

    interface OnClickListener {
        fun onClickEdit()
    }
}