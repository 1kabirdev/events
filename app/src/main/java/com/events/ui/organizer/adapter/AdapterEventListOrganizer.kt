package com.events.ui.organizer.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.events.databinding.*
import com.events.model.list_events.ListEvents
import com.events.model.list_events.Organize
import com.events.ui.comments.CommentsActivity

class AdapterEventListOrganizer(
    private var events: ArrayList<ListEvents>,
    private var listener: OnClickListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var organize: Organize? = null
    private var countEvent = 0
    private var isLoadingAdded = false
    private var errorFailed = false

    fun organizer(organize: Organize) {
        this.organize = organize
    }

    fun infoPage(countEvent: Int) {
        this.countEvent = countEvent
    }

    fun addLoadingFooter() {
        isLoadingAdded = true
    }

    fun removeLoadingFooter() {
        isLoadingAdded = false
    }

    fun addAll(_events: ArrayList<ListEvents>) {
        events.addAll(_events)
        notifyItemInserted(events.size - 1)
    }

    /**
     * Функция для отображаения ошибки при отсутствии интернета
     */
    fun showRetry(show: Boolean) {
        errorFailed = show
        notifyItemChanged(events.size)
    }

    override fun getItemViewType(position: Int): Int {
        return if (isPositionHeader(position)) ORGANIZER
        else if (position == events.size && errorFailed) ERROR_CONNECT
        else if (position == events.size && isLoadingAdded) LOADING
        else ITEM_EVENTS
    }

    private fun isPositionHeader(position: Int): Boolean = position == 0

    companion object {
        const val ITEM_EVENTS = 0
        const val LOADING = 1
        const val ORGANIZER = 2
        const val ERROR_CONNECT = 3
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder? = null
        when (viewType) {
            LOADING -> {
                val loadingViewHolder = ItemLoadingViewBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                viewHolder = LoadingViewHolder(loadingViewHolder)
            }
            ITEM_EVENTS -> {
                val itemEventsHolder = ItemListEventsOrganizerBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                viewHolder = EventViewHolder(itemEventsHolder)
            }

            ORGANIZER -> {
                val profileViewHolder = ItemOrganizerBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                viewHolder = OrganizerViewHolder(profileViewHolder)
            }

            ERROR_CONNECT -> {
                val errorConnection = ItemErrorConnectionViewBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                viewHolder = ErrorConnectViewHolder(errorConnection)
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

            ITEM_EVENTS -> {
                val event = events[position - 1]
                val eventViewHolder = holder as EventViewHolder
                eventViewHolder.bindLoadEvent(event)
            }

            ORGANIZER -> {
                val profileViewHolder = holder as OrganizerViewHolder
                profileViewHolder.bindViewOrganizer(organize!!)
            }

            ERROR_CONNECT -> {
                val errorConnectViewHolder = holder as ErrorConnectViewHolder
                errorConnectViewHolder.binding.notConnection.visibility = View.VISIBLE
                errorConnectViewHolder.binding.btnReplyProfile.setOnClickListener {
                    listener.OnClickReply()
                }
            }
        }
    }

    override fun getItemCount(): Int = events.size + 1

    inner class EventViewHolder(val binding: ItemListEventsOrganizerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindLoadEvent(eventsList: ListEvents) {
            with(binding) {
                textNameEvent.text = eventsList.nameE
                textDateAndTime.text = "${eventsList.dataE} в ${eventsList.timeE}"
                Glide.with(itemView.context).load(eventsList.imageE).into(imageEvents)
                textCityEvents.text = eventsList.cityE
                textTheme.text = eventsList.themeE

                itemView.setOnClickListener {
                    listener.onClickEvent(eventsList.idE.toInt())
                }

                clickDiscussEvent.setOnClickListener {
                    val intent = Intent(itemView.context, CommentsActivity::class.java)
                    intent.putExtra("EVENT_ID", eventsList.idE)
                    intent.putExtra("EVENT_IMAGE", eventsList.imageE)
                    intent.putExtra("EVENT_NAME", eventsList.nameE)
                    intent.putExtra("EVENT_THEME", eventsList.themeE)
                    intent.putExtra("EVENT_DATE", eventsList.dataE + eventsList.timeE)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    inner class LoadingViewHolder(val binding: ItemLoadingViewBinding) :
        RecyclerView.ViewHolder(binding.root)


    inner class ErrorConnectViewHolder(val binding: ItemErrorConnectionViewBinding) :
        RecyclerView.ViewHolder(binding.root)

    inner class OrganizerViewHolder(val binding: ItemOrganizerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindViewOrganizer(organize: Organize) {
            with(binding) {
                Glide.with(itemView.context).load(organize.avatar).into(avatarOrganizer)
                usernameOrganizer.text = "@${organize.username}"
                lastNameOrganizer.text = organize.lastName
                aboutOrganizer.text = organize.about
                textViewCountEvent.text = countEvent.toString()
            }
        }
    }


    interface OnClickListener {
        fun onClickEvent(id: Int)
        fun OnClickReply()
    }
}