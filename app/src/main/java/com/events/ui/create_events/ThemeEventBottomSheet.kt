package com.events.ui.create_events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.events.databinding.FragmentThemeEventBottomSheetBinding
import com.events.model.home.ThemeEvent
import com.events.ui.create_events.adapter.AdapterThemeList
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class ThemeEventBottomSheet(
    private var fragment: CreateEventFragment,
    private var themeEvent: ArrayList<ThemeEvent>
) : BottomSheetDialogFragment(), AdapterThemeList.OnClickListener {

    private lateinit var binding: FragmentThemeEventBottomSheetBinding
    private lateinit var gridLayoutManager: GridLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentThemeEventBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            gridLayoutManager = GridLayoutManager(requireContext(), 3)
            recyclerViewThemeList.layoutManager = gridLayoutManager

            val adapterThemeList = AdapterThemeList(themeEvent, this@ThemeEventBottomSheet)
            recyclerViewThemeList.adapter = adapterThemeList
        }
    }

    override fun onClickTheme(id: Int, name: String) {
        fragment.selectTheme(id, name)
        dismiss()
    }
}