package com.events.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.events.databinding.ItemListHomeThemeBinding
import com.events.model.home.ThemeEvent

class AdapterThemeHome(
    private var themeEvent: MutableList<ThemeEvent>,
    private var listener: OnClickListener
) : RecyclerView.Adapter<AdapterThemeHome.ViewHolder>() {

    inner class ViewHolder(val binding: ItemListHomeThemeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindView(themeEvent: ThemeEvent) {
            with(binding) {
                Glide.with(itemView.context).load(themeEvent.icons).into(imageViewIcons)
                nameTheme.text = themeEvent.name
                itemView.setOnClickListener {
                    listener.onClickTheme(themeEvent.icons, themeEvent.name)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemListHomeThemeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(themeEvent[position])
    }

    override fun getItemCount(): Int = themeEvent.size

    interface OnClickListener {
        fun onClickTheme(icons: String, name: String)
    }
}