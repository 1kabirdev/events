package com.events.ui.home.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.events.databinding.ItemErrorConnectionViewBinding
import com.events.databinding.ItemHeadHomeThemeBinding
import com.events.databinding.ItemListEventsBinding
import com.events.databinding.ItemLoadingViewBinding
import com.events.model.home.ListEvents
import com.events.model.home.ThemeEvent
import com.events.model.theme_event.ThemeEventHome
import com.events.ui.comments.CommentsActivity
import com.events.utill.Constants
import com.events.utill.PreferencesManager
import kotlin.collections.ArrayList

class AdapterEventList(
    private var listener: OnClickListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var arrayTheme: MutableList<ThemeEventHome> = arrayListOf()
    private var eventsList: MutableList<ListEvents> = arrayListOf()

    private var isLoadingAdded = false
    private var errorFailed = false

    fun addEventList(event: ArrayList<ListEvents>) {
        eventsList.addAll(event)
        notifyItemInserted(eventsList.size - 1)
    }

    fun addThemeEvent(themeEventHome: ArrayList<ThemeEventHome>) {
        arrayTheme.addAll(themeEventHome)
        notifyDataSetChanged()
    }

    fun addLoadingFooter() {
        isLoadingAdded = true
    }

    fun removeLoadingFooter() {
        isLoadingAdded = false
    }

    companion object {
        const val ITEM = 0
        const val LOADING = 1
        const val HEAD = 2
        const val ERROR = 3
    }

    override fun getItemViewType(position: Int): Int {
        return if (isPositionHeader(position)) HEAD
        else if (position == eventsList.size && isLoadingAdded) LOADING
        else if (position == eventsList.size && errorFailed) ERROR
        else ITEM
    }

    private fun isPositionHeader(position: Int): Boolean = position == 0

    /**
     * Функция для отображаения ошибки при отсутствии интернета
     */
    fun showRetry(show: Boolean) {
        errorFailed = show
        notifyItemChanged(eventsList.size)
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
            ERROR -> {
                val error = ItemErrorConnectionViewBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                viewHolder = ErrorViewHolder(error)
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
            ERROR -> {
                val errorConnectViewHolder = holder as ErrorViewHolder
                errorConnectViewHolder.binding.notConnection.visibility = View.VISIBLE
                errorConnectViewHolder.binding.btnReplyProfile.setOnClickListener {
                    listener.OnClickReply()
                }
            }
        }
    }

    override fun getItemCount(): Int = eventsList.size + 1

    private inner class LoadingViewHolder(val binding: ItemLoadingViewBinding) :
        RecyclerView.ViewHolder(binding.root)

    private inner class ErrorViewHolder(val binding: ItemErrorConnectionViewBinding) :
        RecyclerView.ViewHolder(binding.root)

    private inner class HeadThemeViewHolder(var binding: ItemHeadHomeThemeBinding) :
        RecyclerView.ViewHolder(binding.root), AdapterThemeHome.OnClickListener {
        fun bindViewTheme(arrayTheme: MutableList<ThemeEventHome>) {
            with(binding) {
                val adapterThemeHome = AdapterThemeHome(arrayTheme, this@HeadThemeViewHolder)
                recyclerViewTheme.adapter = adapterThemeHome
            }
        }

        override fun onClickTheme(icons: String, name: String) {
            listener.onClickTheme(icons, name)
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

                clickDiscussEvent.setOnClickListener {
                    val intent = Intent(itemView.context, CommentsActivity::class.java)
                    intent.putExtra("EVENT_ID", eventsList.idE)
                    intent.putExtra("EVENT_IMAGE", eventsList.imageE)
                    intent.putExtra("EVENT_NAME", eventsList.nameE)
                    intent.putExtra("EVENT_THEME", eventsList.themeE)
                    intent.putExtra("EVENT_DATE", eventsList.dataE + " в " + eventsList.timeE)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    interface OnClickListener {
        fun onClickEvent(event_id: Int, user_id: Int)
        fun onClickMyEvent(event_id: Int)
        fun OnClickReply()
        fun onClickTheme(icons: String, name: String)
    }
}