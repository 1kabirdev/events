package com.events.ui.home.adapter

import android.annotation.SuppressLint
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.events.R
import com.events.databinding.ItemListEventsBinding
import com.events.databinding.ItemLoadingViewBinding
import com.events.model.home.ListEvents
import com.events.utill.Constants
import com.events.utill.PreferencesManager
import java.util.*
import kotlin.collections.ArrayList

class AdapterEventList(
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
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == eventsList.size - 1 && isLoadingAdded) LOADING
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
                val event = eventsList[position]
                val viewHolderEvent = holder as ItemEventViewHolder
                viewHolderEvent.bindLoad(event)
            }
        }
    }

    override fun getItemCount(): Int = eventsList.size

    inner class LoadingViewHolder(val binding: ItemLoadingViewBinding) :
        RecyclerView.ViewHolder(binding.root)

    inner class ItemEventViewHolder(val binding: ItemListEventsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var dateAndTime: Calendar = Calendar.getInstance()
        private val preferencesManager = PreferencesManager(itemView.context)

        @SuppressLint("SetTextI18n")
        fun bindLoad(eventsList: ListEvents) {
            with(binding) {
                textNameEvent.text = eventsList.nameE
                textDateAndTime.text = "${eventsList.dataE} в ${eventsList.timeE}"
                Glide.with(itemView.context).load(eventsList.imageE).into(imageEvents)
                textCityEvents.text = eventsList.cityE
                textTheme.text = eventsList.themeE

                if (eventsList.costE != "" && eventsList.costE != "0") textCost.text =
                    "${eventsList.costE} р"
                else textCost.text = "Бесплатно."


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

                if (eventsList.dataE < getInitialDate() && eventsList.timeE < getInitialTime()) {
                    closeEvents.text = "Не активен"
                } else {
                    closeEvents.text = "Активен"
                    closeEvents.background =
                        ContextCompat.getDrawable(
                            itemView.context,
                            R.drawable.shape_circle_event_right_active
                        )
                }
            }
        }

        private fun getInitialDate(): String {
            return DateUtils.formatDateTime(
                itemView.context,
                dateAndTime.timeInMillis,
                DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_YEAR
            )
        }

        private fun getInitialTime(): String {
            return DateUtils.formatDateTime(
                itemView.context,
                dateAndTime.timeInMillis, DateUtils.FORMAT_SHOW_TIME
            )
        }
    }

    interface OnClickListener {
        fun onClickEvent(event_id: Int, user_id: Int)
        fun onClickMyEvent(event_id: Int)
    }
}