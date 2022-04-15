package com.events.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.events.R


class AdapterImageList(var uriList: ArrayList<Uri>) :
    RecyclerView.Adapter<AdapterImageList.ViewHolder>() {
    private lateinit var viewHolder: ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.item_image_list, parent, false)
        viewHolder = ViewHolder(view)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mImageRecyclerView.setImageURI(uriList[position])
        holder.deleteImage.setOnClickListener {
            deleteItem(uriList[position])
        }
    }

    override fun getItemCount(): Int {
        return uriList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mImageRecyclerView: ImageView = itemView.findViewById(R.id.imageView)
        val deleteImage: ImageView = itemView.findViewById(R.id.deleteImage)

    }

    private fun deleteItem(uri: Uri) {
        try {
            uriList.remove(uri)
            notifyDataSetChanged()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}