package com.events.ui.event.similar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.events.databinding.ItemListSimilarBinding
import com.events.model.similar_event.SimilarList

class AdapterSimilarEvent(
    private var listener: OnClickListener,
    private var similarList: ArrayList<SimilarList>
) : RecyclerView.Adapter<AdapterSimilarEvent.ViewHolder>() {

    inner class ViewHolder(val binding: ItemListSimilarBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindView(similarList: SimilarList) {
            with(binding) {
                itemView.setOnClickListener {
                    listener.onClickEvent(
                        similarList.id_e,
                        similarList.user.id
                    )
                }
                nameEvent.text = similarList.name_e
                textCityEvents.text = similarList.city
                textTheme.text = similarList.theme_e
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemListSimilarBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(similarList[position])
    }

    override fun getItemCount(): Int = similarList.size

    interface OnClickListener {
        fun onClickEvent(event_id: Int, user_id: Int)
    }
}
