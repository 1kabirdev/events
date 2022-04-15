package com.events.ui.home

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.events.R
import com.events.adapter.AdapterImageList


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class HomeFragment : Fragment() {

    private lateinit var selectImage: TextView
    private lateinit var clearImageList: TextView
    var uri: ArrayList<Uri> = ArrayList()
    lateinit var recyclerView: RecyclerView
    private lateinit var adapterImageList: AdapterImageList

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        selectImage = view.findViewById(R.id.selectImage)
        clearImageList = view.findViewById(R.id.clearImageList)
        recyclerView = view.findViewById(R.id.recyclerImageList)


        selectImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            startActivityForResult(intent, 1)
        }

        adapterImageList = AdapterImageList(uri)
        recyclerView.adapter = adapterImageList

        clearImageList.setOnClickListener {
            uri.clear()
            adapterImageList.notifyDataSetChanged()
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                if (data!!.clipData != null) {
                    val count: Int = data.clipData!!.itemCount
                    for (i in 0 until count) {
                        uri.add(data.clipData!!.getItemAt(i).uri)
                    }
                    adapterImageList.notifyDataSetChanged()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Выберите фото больше 1-го",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else if (data!!.data != null) {
                val imagePath: String? = data.data!!.path

            }
        }
    }

}