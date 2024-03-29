package com.events.ui.theme_list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.events.R
import com.events.databinding.*
import com.events.model.theme_event.ListEvents
import com.events.utill.Constants
import com.events.utill.PreferencesManager

class AdapterThemeListEvent(
    private var listener: OnClickListener,
    private var listEvent: ArrayList<ListEvents>,
    private var name: String,
    private var icons: String,
    private var countEvent: String,
    private var subscribe: Boolean
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var isLoadingAdded = false
    private var errorFailed = false

    fun addEventList(event: ArrayList<ListEvents>) {
        listEvent.addAll(event)
        notifyItemInserted(listEvent.size - 1)
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
        else if (position == listEvent.size && isLoadingAdded) LOADING
        else if (position == listEvent.size && errorFailed) ERROR
        else ITEM
    }

    private fun isPositionHeader(position: Int): Boolean = position == 0

    /**
     * Функция для отображаения ошибки при отсутствии интернета
     */
    fun showRetry(show: Boolean) {
        errorFailed = show
        notifyItemChanged(listEvent.size)
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
                val binding = ItemListThemeEventsBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                viewHolder = EventViewHolder(binding)
            }

            HEAD -> {
                val head = ItemHeadThemeEventBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                viewHolder = HeadViewHolder(head)
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
                val event = listEvent[position - 1]
                val viewHolderEvent = holder as EventViewHolder
                viewHolderEvent.bindView(event)
            }
            HEAD -> {
                val headHolder = holder as HeadViewHolder
                headHolder.bindViewHead(name, icons, countEvent, subscribe)
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

    override fun getItemCount(): Int = listEvent.size + 1

    private inner class LoadingViewHolder(val binding: ItemLoadingViewBinding) :
        RecyclerView.ViewHolder(binding.root)

    private inner class ErrorViewHolder(val binding: ItemErrorConnectionViewBinding) :
        RecyclerView.ViewHolder(binding.root)

    private inner class HeadViewHolder(val binding: ItemHeadThemeEventBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val preferencesManager = PreferencesManager(itemView.context)
        private var flag: Boolean = false

        fun bindViewHead(name: String, icons: String, countEvent: String, subscribe: Boolean) {

            with(binding) {

                Glide.with(itemView.context).load(icons).into(iconsTheme)
                textTheme.text = name
                countEventTextView.text = "Мероприятия: $countEvent"

                if (subscribe) {
                    flag = false
                    btnSubscribe.text = "Отписаться"
                    btnSubscribe.background = ContextCompat.getDrawable(
                        itemView.context,
                        R.drawable.shape_btn_sub_true_bkg
                    )
                } else {
                    flag = true
                    btnSubscribe.text = "Подписаться"
                    btnSubscribe.background =
                        ContextCompat.getDrawable(itemView.context, R.drawable.shape_btn_bkg)
                }

                btnSubscribe.setOnClickListener {
                    if (preferencesManager.getBoolean(Constants.SIGN_UP)) {
                        if (flag) {
                            flag = false
                            btnSubscribe.background = ContextCompat.getDrawable(
                                itemView.context,
                                R.drawable.shape_btn_sub_true_bkg
                            )
                            btnSubscribe.text = "Отписаться"
                            listener.onClickSubscribe(name)
                        } else {
                            flag = true
                            btnSubscribe.background =
                                ContextCompat.getDrawable(
                                    itemView.context,
                                    R.drawable.shape_btn_bkg
                                )
                            btnSubscribe.text = "Подписаться"
                            listener.onClickSubscribe(name)
                        }
                    } else {
                        Toast.makeText(
                            itemView.context,
                            "Это действие требует авторизации",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private inner class EventViewHolder(val binding: ItemListThemeEventsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val preferencesManager = PreferencesManager(itemView.context)

        fun bindView(listEvents: ListEvents) {
            with(binding) {
                textNameEvent.text = listEvents.nameE
                textDateAndTime.text = "${listEvents.dataE} в ${listEvents.timeE}"
                Glide.with(itemView.context).load(listEvents.imageE).into(imageEvents)
                textCityEvents.text = listEvents.cityE
                textTheme.text = listEvents.themeE

                clickDiscussEvent.setOnClickListener {
                    listener.onClickEventDiscuss(
                        listEvents.idE.toInt(),
                        listEvents.imageE,
                        listEvents.nameE,
                        listEvents.themeE,
                        listEvents.dataE,
                        listEvents.timeE
                    )
                }

                itemView.setOnClickListener {
                    if (preferencesManager.getString(Constants.USER_ID) != listEvents.user!!
                            .userId
                    ) {
                        listener.onClickEvent(
                            listEvents.idE.toInt(),
                            listEvents.user!!.userId.toInt()
                        )
                    } else {
                        listener.onClickMyEvent(listEvents.idE.toInt())
                    }
                }
            }
        }
    }

    interface OnClickListener {
        fun onClickEvent(
            event_id: Int,
            user_id: Int
        )

        fun onClickMyEvent(event_id: Int)

        fun OnClickReply()

        fun onClickEventDiscuss(
            event_id: Int,
            event_image: String,
            event_name: String,
            event_theme: String,
            event_date: String,
            event_time: String
        )

        fun onClickSubscribe(name: String)
    }
}