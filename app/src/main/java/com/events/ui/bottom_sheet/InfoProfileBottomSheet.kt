package com.events.ui.bottom_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.events.databinding.FragmentInfoProfileBottomSheetBinding
import com.events.ui.profile.ProfileFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class InfoProfileBottomSheet(private var frament: ProfileFragment) : BottomSheetDialogFragment() {
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
        binding.logautAccount.setOnClickListener {
            frament.getLogoutAccount()
            dismiss()
        }
    }
}