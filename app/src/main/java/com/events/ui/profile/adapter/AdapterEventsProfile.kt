package com.events.ui.profile.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.events.databinding.ItemErrorConnectionViewBinding
import com.events.databinding.ItemListMyEventsBinding
import com.events.databinding.ItemLoadingViewBinding
import com.events.databinding.ItemProfileBinding
import com.events.model.profile.ResponseInfoProfile
import com.events.ui.event.MyEventsActivity

class AdapterEventsProfile(
    private var events: ArrayList<ResponseInfoProfile.ResponseEvents>,
    private var listener: OnClickListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var subscribeList: ArrayList<ResponseInfoProfile.Subscribe> = arrayListOf()
    private var profileData: ResponseInfoProfile.ProfileData? = null
    private var countEvent = 0
    private var isLoadingAdded = false
    private var errorFailed = false

    fun addLoadingFooter() {
        isLoadingAdded = true
    }

    fun removeLoadingFooter() {
        isLoadingAdded = false
    }

    fun addAll(_events: ArrayList<ResponseInfoProfile.ResponseEvents>) {
        events.addAll(_events)
        notifyItemInserted(events.size - 1)
    }

    fun profile(profileData: ResponseInfoProfile.ProfileData) {
        this.profileData = profileData
    }

    fun infoCountEvent(countEvent: Int) {
        this.countEvent = countEvent
    }

    fun addSubscribeList(subscribe: ArrayList<ResponseInfoProfile.Subscribe>) {
        subscribeList.addAll(subscribe)
    }

    /**
     * Функция для отображаения ошибки при отсутствии интернета
     */
    fun showRetry(show: Boolean) {
        errorFailed = show
        notifyItemChanged(events.size)
    }

    override fun getItemViewType(position: Int): Int {
        return if (isPositionHeader(position)) PROFILE
        else if (position == events.size && errorFailed) ERROR_CONNECT
        else if (position == events.size && isLoadingAdded) LOADING
        else ITEM_EVENTS
    }

    private fun isPositionHeader(position: Int): Boolean = position == 0

    companion object {
        const val ITEM_EVENTS = 0
        const val LOADING = 1
        const val PROFILE = 2
        const val ERROR_CONNECT = 3
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder? = null
        when (viewType) {
            LOADING -> {
                val loadingViewHolder = ItemLoadingViewBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                viewHolder = LoadingViewHolder(loadingViewHolder)
            }
            ITEM_EVENTS -> {
                val itemEventsHolder = ItemListMyEventsBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                viewHolder = EventsViewHolder(itemEventsHolder)
            }

            PROFILE -> {
                val profileViewHolder = ItemProfileBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                viewHolder = ProfileViewHolder(profileViewHolder)
            }

            ERROR_CONNECT -> {
                val errorConnection = ItemErrorConnectionViewBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                viewHolder = ErrorConnectionViewHolder(errorConnection)
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
                val eventViewHolder = holder as EventsViewHolder
                eventViewHolder.bindLoad(event)
            }

            PROFILE -> {
                val profileViewHolder = holder as ProfileViewHolder
                profileViewHolder.bindViewProfile(profileData!!)
            }

            ERROR_CONNECT -> {
                val errorConnectViewHolder = holder as ErrorConnectionViewHolder
                errorConnectViewHolder.binding.notConnection.visibility = View.VISIBLE
                errorConnectViewHolder.binding.btnReplyProfile.setOnClickListener {
                    listener.OnClickReply()
                }
            }
        }
    }

    override fun getItemCount(): Int = events.size + 1

    inner class EventsViewHolder(val binding: ItemListMyEventsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindLoad(eventsList: ResponseInfoProfile.ResponseEvents) {
            with(binding) {
                textNameEvent.text = eventsList.nameE
                textDateAndTime.text = "${eventsList.dataE} в ${eventsList.timeE}"
                Glide.with(itemView.context).load(eventsList.imageE).into(imageEvents)
                textCityEvents.text = eventsList.cityE
                textTheme.text = eventsList.themeE

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, MyEventsActivity::class.java)
                    intent.putExtra("EVENTS_ID", eventsList.idE)
                    it.context.startActivity(intent)
                }
                clickDiscussEvent.setOnClickListener {
                    listener.onClickDiscuss(
                        eventsList.idE.toInt(),
                        eventsList.imageE,
                        eventsList.nameE,
                        eventsList.themeE,
                        eventsList.dataE,
                        eventsList.timeE
                    )
                }
            }
        }
    }

    inner class ProfileViewHolder(val binding: ItemProfileBinding) :
        RecyclerView.ViewHolder(binding.root), AdapterSubscribe.OnClickListener {

        fun bindViewProfile(profileData: ResponseInfoProfile.ProfileData) {
            with(binding) {
                textViewCountEvent.text = countEvent.toString()
                Glide.with(itemView.context).load(profileData.avatar).into(avatarProfile)
                usernameProfile.text = "@${profileData.username}"
                aboutProfile.text = profileData.about
                lastNameProfile.text = profileData.last_name
                btnClickEditProfile.setOnClickListener {
                    listener.onClickUserEdit(profileData)
                }
                countSubscriber.text = "Подписки ${subscribeList.size}"

                val adapterSubscribe = AdapterSubscribe(subscribeList, this@ProfileViewHolder)
                recyclerViewSubscribe.adapter = adapterSubscribe
            }
        }

        override fun onClickTheme(name: String,icons: String) {
            listener.onClickSubscribe(name,icons)
        }
    }

    inner class LoadingViewHolder(val binding: ItemLoadingViewBinding) :
        RecyclerView.ViewHolder(binding.root)

    inner class ErrorConnectionViewHolder(val binding: ItemErrorConnectionViewBinding) :
        RecyclerView.ViewHolder(binding.root)


    interface OnClickListener {
        fun onClickUserEdit(profileData: ResponseInfoProfile.ProfileData)
        fun OnClickReply()
        fun onClickDiscuss(
            id: Int,
            image: String,
            name: String,
            theme: String,
            date: String,
            time: String
        )

        fun onClickSubscribe(name: String,icons:String)
    }
}