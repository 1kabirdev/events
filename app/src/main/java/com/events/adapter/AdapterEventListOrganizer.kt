package com.events.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.events.R
import com.events.databinding.ItemListEventsOrganizerBinding
import com.events.model.list_events.ListEvents
import com.events.ui.event.EventsActivity
import com.events.ui.event.MyEventsActivity
import com.events.utill.Constants
import com.events.utill.PreferencesManager
import com.squareup.picasso.Picasso

class AdapterEventListOrganizer(private var events: ArrayList<ListEvents>) :
    RecyclerView.Adapter<AdapterEventListOrganizer.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterEventListOrganizer.ViewHolder {
        return ViewHolder(
            ItemListEventsOrganizerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AdapterEventListOrganizer.ViewHolder, position: Int) {
        val eventsLite = events[position]
        holder.bindLoad(eventsLite)
    }

    override fun getItemCount(): Int {
        return events.size
    }

    inner class ViewHolder(val binding: ItemListEventsOrganizerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private var preferencesManager = PreferencesManager(itemView.context)

        @SuppressLint("SetTextI18n")
        fun bindLoad(eventsList: ListEvents) {
            with(binding) {
                textNameEvent.text = eventsList.nameE
                textDateAndTime.text = "${eventsList.dataE} в ${eventsList.timeE}"
                Glide.with(itemView.context).load(eventsList.imageE).into(imageEvents)
                textCityEvents.text = eventsList.cityE
                textTheme.text = eventsList.themeE
                if (eventsList.costE != "" && eventsList.costE != "0") {
                    textCost.text = "${eventsList.costE} р"
                } else {
                    textCost.text = "Бесплатно."
                }

                itemView.setOnClickListener {
                    if (preferencesManager.getString(Constants.USER_ID) != eventsList.user!!
                            .userId
                    ) {
                        val intent = Intent(itemView.context, EventsActivity::class.java)
                        intent.putExtra("EVENTS_ID", eventsList.idE)
                        intent.putExtra("USER_ID", eventsList.user!!.userId)
                        it.context.startActivity(intent)
                    } else {
                        val intent = Intent(itemView.context, MyEventsActivity::class.java)
                        intent.putExtra("EVENTS_ID", eventsList.idE)
                        it.context.startActivity(intent)
                    }
                }
            }
        }
    }
}