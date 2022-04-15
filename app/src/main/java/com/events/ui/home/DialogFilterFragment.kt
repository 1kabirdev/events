package com.events.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.events.databinding.FragmentDialogFilterBinding
import com.events.utill.Constants
import com.events.utill.SharedPreferences

class DialogFilterFragment(private var fragment: HomeEventsFragment) : DialogFragment() {

    private lateinit var binding: FragmentDialogFilterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDialogFilterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog!!.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        binding.all.setOnClickListener {
            fragment.filter("all")
            SharedPreferences.setEventType("all", requireContext())
            dismiss()
        }
        binding.city.setOnClickListener {
            fragment.filter("Махачкала")
            SharedPreferences.setEventType("Махачкала", requireContext())
            dismiss()
        }
    }

}