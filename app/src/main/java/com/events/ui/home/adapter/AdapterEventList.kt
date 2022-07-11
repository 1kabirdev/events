package com.events.ui.home.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.events.R
import com.events.databinding.ItemListEventsBinding
import com.events.model.list_events.ListEvents
import com.events.ui.event.EventsActivity
import com.events.ui.event.MyEventsActivity
import com.events.utill.SharedPreferences
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.collections.ArrayList

class AdapterEventList(private var events: ArrayList<ListEvents>) :
    RecyclerView.Adapter<AdapterEventList.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemListEventsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindLoad(events[position])
    }

    override fun getItemCount(): Int {
        return events.size
    }

    inner class ViewHolder(val binding: ItemListEventsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var dateAndTime: Calendar = Calendar.getInstance()

        @SuppressLint("SetTextI18n")
        fun bindLoad(eventsList: ListEvents) {
            with(binding) {
                textNameEvent.text = eventsList.getNameE()
                textDateAndTime.text = "${eventsList.getDataE()} в ${eventsList.getTimeE()}"
                Picasso.get().load(eventsList.getImageE()).into(imageEvents)
                textCityEvents.text = eventsList.getCityE()
                textTheme.text = eventsList.getThemeE()

                if (eventsList.getCostE() != "" && eventsList.getCostE() != "0") textCost.text =
                    "${eventsList.getCostE()} р"
                else textCost.text = "Бесплатно."


                itemView.setOnClickListener {
                    if (SharedPreferences.loadIdUser(itemView.context)
                            .toString() != eventsList.getUser()!!.getUserId()
                    ) {
                        val intent = Intent(itemView.context, EventsActivity::class.java)
                        intent.putExtra("EVENTS_ID", eventsList.getIdE())
                        intent.putExtra("USER_ID", eventsList.getUser()!!.getUserId())
                        it.context.startActivity(intent)
                    } else {
                        val intent = Intent(itemView.context, MyEventsActivity::class.java)
                        intent.putExtra("EVENTS_ID", eventsList.getIdE())
                        it.context.startActivity(intent)
                    }
                }

                if (eventsList.getDataE() < getInitialDate() || eventsList.getTimeE() < getInitialTime()) {
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
            val date = DateUtils.formatDateTime(
                itemView.context,
                dateAndTime.timeInMillis,
                DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_YEAR
            )
            return date
        }

        private fun getInitialTime(): String {
            val time = DateUtils.formatDateTime(
                itemView.context,
                dateAndTime.timeInMillis, DateUtils.FORMAT_SHOW_TIME
            )
            return time
        }
    }
}