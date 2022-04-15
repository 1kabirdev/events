package com.events.ui.create_events

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.ProgressDialog
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.ActivityNotFoundException
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.events.App
import com.events.databinding.FragmentCreateEventBinding
import com.events.model.create_event.ResponseCreateEvents
import com.events.utill.SharedPreferences
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.util.*


class CreateEventFragment : Fragment(), CreateEventsController.View {

    private lateinit var binding: FragmentCreateEventBinding
    private lateinit var progressBar: ProgressDialog
    private lateinit var presenter: CreateEventsPresenter
    var dateAndTime: Calendar = Calendar.getInstance()
    private var inp: InputStream? = null
    private val INTENT_REQUEST_CODE = 100
    private var dateEvent: Boolean = false
    private var timeEvent: Boolean = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateEventBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = CreateEventsPresenter((requireContext().applicationContext as App).dataManager)
        presenter.attachView(this)

        binding.constraintDateCreateEvents.setOnClickListener {
            setDate(it.rootView)
        }

        binding.constraintTimeCreateEvents.setOnClickListener {
            setTime(it.rootView)
        }

        binding.cardViewImage.setOnClickListener {
            intentImageGallery()
        }

        binding.deleteImageSelected.setOnClickListener {
            binding.titleSelectImage.visibility = View.VISIBLE
            binding.deleteImageSelected.visibility = View.GONE
            binding.imageView.setImageDrawable(null)
            binding.imageView.destroyDrawingCache()
        }

        binding.btnClearData.setOnClickListener {
            getClearView()
        }

        binding.btnCreateEvents.setOnClickListener {
            when {
                dateEvent != true -> {
                    Toast.makeText(requireContext(), "Укажите дату события", Toast.LENGTH_SHORT)
                        .show()
                }
                timeEvent != true -> {
                    Toast.makeText(requireContext(), "Укажите время проведения", Toast.LENGTH_SHORT)
                        .show()
                }
                inp == null -> {
                    Toast.makeText(requireContext(), "Выберите картинку", Toast.LENGTH_SHORT)
                        .show()
                }
                binding.editTextNameEvents.text.toString() == "" -> {
                    Toast.makeText(
                        requireContext(),
                        "Название не должно быть пустым",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
                binding.editTextDescEvents.text.toString() == "" -> {
                    Toast.makeText(
                        requireContext(),
                        "Описание не должно быть пустым",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
                binding.editTextTheme.text.toString() == "" -> {
                    Toast.makeText(
                        requireContext(),
                        "Тема события не должно быть пустым",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
                binding.editTextCityEvents.text.toString() == "" -> {
                    Toast.makeText(
                        requireContext(),
                        "Город проведения не должно быть пустым",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
                else -> {
                    presenter.responseCreateEvents(
                        SharedPreferences.loadIdUser(requireContext()).toString(),
                        binding.editTextNameEvents.text.toString(),
                        binding.editTextDescEvents.text.toString(),
                        binding.textDateCreateEvents.text.toString(),
                        binding.textTimeCreateEvents.text.toString(),
                        binding.editTextTheme.text.toString(),
                        binding.editTextCityEvents.text.toString(),
                        binding.editTextEntranceEvents.text.toString(),
                        getBytes(inp!!)!!
                    )
                }
            }
        }
    }

    @SuppressLint("Recycle")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == INTENT_REQUEST_CODE) {
            if (resultCode == AppCompatActivity.RESULT_OK) {
                val selectedImage: Uri = data!!.data!!
                val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                val cursor: Cursor =
                    requireContext().contentResolver.query(
                        selectedImage,
                        filePathColumn,
                        null,
                        null,
                        null
                    )!!
                cursor.moveToFirst()
                val columnIndex: Int = cursor.getColumnIndexOrThrow(filePathColumn[0])
                val picturePath = cursor.getString(columnIndex)
                if (picturePath != "") {
                    binding.deleteImageSelected.visibility = View.VISIBLE
                    binding.titleSelectImage.visibility = View.GONE
                    inp = requireContext().contentResolver.openInputStream(selectedImage)!!
                    binding.imageView.setImageURI(selectedImage)
                } else {
                    binding.titleSelectImage.visibility = View.VISIBLE
                    binding.deleteImageSelected.visibility = View.GONE
                }

            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun getClearView() {
        binding.titleSelectImage.visibility = View.VISIBLE
        binding.deleteImageSelected.visibility = View.GONE
        binding.imageView.setImageDrawable(null)
        binding.imageView.destroyDrawingCache()
        binding.editTextNameEvents.setText("")
        binding.editTextDescEvents.setText("")
        binding.textDateCreateEvents.text = "Указать дату"
        binding.textTimeCreateEvents.text = "Указать время"
        binding.editTextTheme.setText("")
        binding.editTextCityEvents.setText("")
        binding.editTextEntranceEvents.setText("")
        dateEvent = false
        timeEvent = false
    }


    private fun intentImageGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        try {
            startActivityForResult(intent, INTENT_REQUEST_CODE)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
        }
    }

    @Throws(IOException::class)
    fun getBytes(inp: InputStream): ByteArray? {
        val byteBuff = ByteArrayOutputStream()
        val buffSize = 1024
        val buff = ByteArray(buffSize)
        var len = 0
        while (inp.read(buff).also { len = it } != -1) {
            byteBuff.write(buff, 0, len)
        }
        return byteBuff.toByteArray()
    }

    private fun setDate(v: View) {
        DatePickerDialog(
            v.context, date,
            dateAndTime[Calendar.YEAR],
            dateAndTime[Calendar.MONTH],
            dateAndTime[Calendar.DAY_OF_MONTH]
        )
            .show()
    }

    private fun setTime(v: View) {
        TimePickerDialog(
            v.context, time,
            dateAndTime[Calendar.HOUR_OF_DAY],
            dateAndTime[Calendar.MINUTE], true
        )
            .show()
    }

    private fun setInitialDate() {
        dateEvent = true
        binding.textDateCreateEvents.text = DateUtils.formatDateTime(
            requireContext(),
            dateAndTime.timeInMillis,
            DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_YEAR
        )
    }

    private fun setInitialTime() {
        timeEvent = true
        binding.textTimeCreateEvents.text = DateUtils.formatDateTime(
            requireContext(),
            dateAndTime.timeInMillis, DateUtils.FORMAT_SHOW_TIME
        )
    }

    private var time =
        OnTimeSetListener { view, hourOfDay, minute ->
            dateAndTime[Calendar.HOUR_OF_DAY] = hourOfDay
            dateAndTime[Calendar.MINUTE] = minute
            setInitialTime()
        }

    private var date =
        OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            dateAndTime[Calendar.YEAR] = year
            dateAndTime[Calendar.MONTH] = monthOfYear
            dateAndTime[Calendar.DAY_OF_MONTH] = dayOfMonth
            setInitialDate()
        }

    override fun createEvents(responseCreateEvents: ResponseCreateEvents) {
        when {
            responseCreateEvents.getStatus().toBoolean() == true -> {
                getClearView()
                Toast.makeText(requireContext(), "Событие опубликовано.", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun showProgress() {
        progressBar =
            ProgressDialog.show(requireContext(), "Загрузка.", "Подождите пожалуйста...", false)
    }

    override fun hideProgress() {
        progressBar.dismiss()
    }

    override fun noConnection() {
        progressBar.dismiss()
        Toast.makeText(requireContext(), "Проверьте подключение интернета.", Toast.LENGTH_SHORT)
            .show()
    }
}