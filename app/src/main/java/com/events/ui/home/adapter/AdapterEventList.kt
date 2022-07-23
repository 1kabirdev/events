package com.events.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.events.databinding.ItemHeadHomeThemeBinding
import com.events.databinding.ItemListEventsBinding
import com.events.databinding.ItemLoadingViewBinding
import com.events.model.home.ListEvents
import com.events.model.home.ThemeEvent
import com.events.utill.Constants
import com.events.utill.PreferencesManager
import kotlin.collections.ArrayList

class AdapterEventList(
    private var arrayTheme: MutableList<ThemeEvent>,
    private var listener: OnClickListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var eventsList: MutableList<ListEvents> = arrayListOf()
    private var isLoadingAdded = false

    fun addComments(event: ArrayList<ListEvents>) {
        eventsList.addAll(event)
        notifyItemInserted(eventsList.size - 1)
    }

    fun addLoadingFooter(show: Boolean) {
        isLoadingAdded = show
    }

    companion object {
        const val ITEM = 0
        const val LOADING = 1
        const val HEAD = 2
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) HEAD
        else if (position == eventsList.size && isLoadingAdded) LOADING
        else ITEM
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder? = null
        when (viewType) {
            LOADING -> {
                val viewLoading = ItemLoadingViewBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                viewHolder = LoadingViewHolder(viewLoading)
            }
            ITEM -> {
                val binding = ItemListEventsBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                viewHolder = ItemEventViewHolder(binding)
            }
            HEAD -> {
                val headView = ItemHeadHomeThemeBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                viewHolder = HeadThemeViewHolder(headView)
            }
        }
        return viewHolder!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            LOADING -> {
                val loadingViewHolder = holder as LoadingViewHolder
                loadingViewHolder.binding.progressViewComment.visibility = View.VISIBLE
            }
            ITEM -> {
                val event = eventsList[position - 1]
                val viewHolderEvent = holder as ItemEventViewHolder
                viewHolderEvent.bindLoad(event)
            }

            HEAD -> {
                val headViewHolder = holder as HeadThemeViewHolder
                headViewHolder.bindViewTheme(arrayTheme)
            }
        }
    }

    override fun getItemCount(): Int = eventsList.size + 1

    private inner class LoadingViewHolder(val binding: ItemLoadingViewBinding) :
        RecyclerView.ViewHolder(binding.root)

    private inner class HeadThemeViewHolder(var binding: ItemHeadHomeThemeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindViewTheme(arrayTheme: MutableList<ThemeEvent>) {
            with(binding) {
                val adapterThemeHome = AdapterThemeHome(arrayTheme)
                recyclerViewTheme.adapter = adapterThemeHome
            }
        }
    }

    private inner class ItemEventViewHolder(val binding: ItemListEventsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val preferencesManager = PreferencesManager(itemView.context)

        fun bindLoad(eventsList: ListEvents) {
            with(binding) {
                textNameEvent.text = eventsList.nameE
                textDateAndTime.text = "${eventsList.dataE} в ${eventsList.timeE}"
                Glide.with(itemView.context).load(eventsList.imageE).into(imageEvents)
                textCityEvents.text = eventsList.cityE
                textTheme.text = eventsList.themeE

                itemView.setOnClickListener {
                    if (preferencesManager.getString(Constants.USER_ID) != eventsList.user!!
                            .userId
                    ) {
                        listener.onClickEvent(
                            eventsList.idE.toInt(),
                            eventsList.user!!.userId.toInt()
                        )
                    } else {
                        listener.onClickMyEvent(eventsList.idE.toInt())
                    }
                }
            }
        }
    }

    interface OnClickListener {
        fun onClickEvent(event_id: Int, user_id: Int)
        fun onClickMyEvent(event_id: Int)
    }
}