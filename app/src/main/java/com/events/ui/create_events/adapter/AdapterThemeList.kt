package com.events.ui.create_events.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.events.databinding.ItemListThemeEventBinding
import com.events.model.home.ThemeEvent

class AdapterThemeList(
    private var themeList: ArrayList<ThemeEvent>,
    private var listener: OnClickListener
) : RecyclerView.Adapter<AdapterThemeList.ViewHolder>() {


    inner class ViewHolder(val binding: ItemListThemeEventBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindView(themeEvent: ThemeEvent) {
            with(binding) {
                Glide.with(itemView.context).load(themeEvent.icons).into(imageViewIcons)
                nameTheme.text = themeEvent.name

                itemView.setOnClickListener {
                    listener.onClickTheme(themeEvent.id, themeEvent.name)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemListThemeEventBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(themeList[position])
    }

    override fun getItemCount(): Int = themeList.size

    interface OnClickListener {
        fun onClickTheme(
            id: Int, name: String
        )
    }
}