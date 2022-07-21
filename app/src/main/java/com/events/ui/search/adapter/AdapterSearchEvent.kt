package com.events.ui.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.events.databinding.ItemListEventSearchBinding
import com.events.model.search.Event

class AdapterSearchEvent(
    private var eventSearch: ArrayList<Event>,
    private var listener: OnClickListener
) : RecyclerView.Adapter<AdapterSearchEvent.SearchViewHolder>() {

    inner class SearchViewHolder(val binding: ItemListEventSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindViewEvent(event: Event) {
            with(binding) {
                Glide.with(itemView.context).load(event.image).into(imageViewEvent)
                nameEvent.text = event.name
                themeEvent.text = event.theme

                itemView.setOnClickListener {
                    listener.onClickEvent(event.id, event.user_id)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(
            ItemListEventSearchBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bindViewEvent(eventSearch[position])
    }

    override fun getItemCount(): Int = eventSearch.size

    interface OnClickListener {
        fun onClickEvent(id: Int, user_id: Int)
    }
}