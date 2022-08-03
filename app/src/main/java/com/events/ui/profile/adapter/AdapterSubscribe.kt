package com.events.ui.profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.events.databinding.ItemListSubscribeBinding
import com.events.model.profile.ResponseInfoProfile

class AdapterSubscribe(
    private var subscribeList: ArrayList<ResponseInfoProfile.Subscribe>,
    private var listener: OnClickListener
) : RecyclerView.Adapter<AdapterSubscribe.ViewHolder>() {

    inner class ViewHolder(val binding: ItemListSubscribeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindViewSubscribe(subscribe: ResponseInfoProfile.Subscribe) {
            with(binding) {
                nameThemeSubscribe.text = "#${subscribe.theme}"
                itemView.setOnClickListener {
                    listener.onClickTheme(subscribe.theme,subscribe.icons)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemListSubscribeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindViewSubscribe(subscribeList[position])
    }

    override fun getItemCount(): Int = subscribeList.size

    interface OnClickListener {
        fun onClickTheme(name: String,icons:String)
    }
}