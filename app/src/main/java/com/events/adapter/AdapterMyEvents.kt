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
import com.events.databinding.ItemListMyEventsBinding
import com.events.model.my_events.MyEventsList
import com.events.model.profile.ResponseEvents
import com.events.ui.event.MyEventsActivity
import com.squareup.picasso.Picasso

class AdapterMyEvents(private var events: ArrayList<ResponseEvents>) :
    RecyclerView.Adapter<AdapterMyEvents.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterMyEvents.ViewHolder {
        return ViewHolder(
            ItemListMyEventsBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: AdapterMyEvents.ViewHolder, position: Int) {
        val eventsLite = events[position]
        holder.bindLoad(eventsLite)
    }

    override fun getItemCount(): Int = events.size

    inner class ViewHolder(val binding: ItemListMyEventsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bindLoad(eventsList: ResponseEvents) {
            with(binding) {
                textNameEvent.text = eventsList.nameE
                textDateAndTime.text = "${eventsList.dataE} в ${eventsList.timeE}"
                Glide.with(itemView.context).load(eventsList.imageE).into(imageEvents)
                textCityEvents.text = eventsList.cityE
                textTheme.text = eventsList.themeE

                if (eventsList.costE != "" && eventsList.costE != "0")
                    textCost.text = "${eventsList.costE} р"
                else textCost.text = "Бесплатно."

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, MyEventsActivity::class.java)
                    intent.putExtra("EVENTS_ID", eventsList.idE)
                    it.context.startActivity(intent)
                }
            }
        }
    }
}