package com.events.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.events.R
import com.events.model.my_events.MyEventsList
import com.events.ui.event.MyEventsActivity
import com.squareup.picasso.Picasso

class AdapterMyEvents(private var events: ArrayList<MyEventsList>) :
    RecyclerView.Adapter<AdapterMyEvents.ViewHolder>() {
    private lateinit var viewHolder: ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterMyEvents.ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_list_my_events, parent, false)
        viewHolder = ViewHolder(view)
        return viewHolder
    }

    override fun onBindViewHolder(holder: AdapterMyEvents.ViewHolder, position: Int) {
        val eventsLite = events[position]
        viewHolder.bindLoad(holder, eventsLite)
    }

    override fun getItemCount(): Int {
        return events.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val textNameEvent: TextView = view.findViewById(R.id.textNameEvent)
        private val textDateAndTime: TextView = view.findViewById(R.id.textDateAndTime)
        private val imageEvents: ImageView = view.findViewById(R.id.imageEvents)
        private val textCityEvents: TextView = view.findViewById(R.id.textCityEvents)
        private val textTheme: TextView = view.findViewById(R.id.textTheme)
        private val textCost: TextView = view.findViewById(R.id.textCost)

        @SuppressLint("SetTextI18n")
        fun bindLoad(holder: ViewHolder, eventsList: MyEventsList) {
            holder.textNameEvent.text = eventsList.getNameE()
            holder.textDateAndTime.text = "${eventsList.getDataE()} в ${eventsList.getTimeE()}"
            Picasso.get().load(eventsList.getImageE()).into(holder.imageEvents)
            holder.textCityEvents.text = eventsList.getCityE()
            holder.textTheme.text = eventsList.getThemeE()
            if (eventsList.getCostE() != "" && eventsList.getCostE() != "0") {
                holder.textCost.text = "${eventsList.getCostE()} р"
            } else {
                holder.textCost.text = "Бесплатно."
            }

            holder.itemView.setOnClickListener {
                val intent = Intent(itemView.context, MyEventsActivity::class.java)
                intent.putExtra("EVENTS_ID", eventsList.getIdE())
                it.context.startActivity(intent)
            }
        }
    }
}