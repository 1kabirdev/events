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
import com.events.MainActivity
import com.events.databinding.FragmentCreateEventBinding
import com.events.model.create_event.ResponseCreateEvents
import com.events.model.home.ThemeEvent
import com.events.ui.bottom_sheet.InfoProfileBottomSheet
import com.events.utill.Constants
import com.events.utill.PreferencesManager
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.util.*


@Suppress("DEPRECATION")
class CreateEventFragment : Fragment(), CreateEventsController.View {

    private var presenter: CreateEventsPresenter =
        CreateEventsPresenter((requireContext().applicationContext as App).dataManager)
    private var preferencesManager = PreferencesManager(requireContext())
    private lateinit var binding: FragmentCreateEventBinding
    private lateinit var progressBar: ProgressDialog
    var dateAndTime: Calendar = Calendar.getInstance()
    private var inp: InputStream? = null
    private val INTENT_REQUEST_CODE = 100
    private var dateEvent: Boolean = false
    private var timeEvent: Boolean = false
    private var arrayTheme: ArrayList<ThemeEvent> = arrayListOf()
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
        initThemeList()

        with(binding) {

            editTextTheme.setOnClickListener {
                (requireActivity() as MainActivity).createDialogFragment(
                    ThemeEventBottomSheet(arrayTheme)
                )
            }

            constraintDateCreateEvents.setOnClickListener {
                setDate(it.rootView)
            }

            constraintTimeCreateEvents.setOnClickListener {
                setTime(it.rootView)
            }

            cardViewImage.setOnClickListener {
                intentImageGallery()
            }

            deleteImageSelected.setOnClickListener {
                titleSelectImage.visibility = View.VISIBLE
                deleteImageSelected.visibility = View.GONE
                imageView.setImageDrawable(null)
                imageView.destroyDrawingCache()
            }

            btnClearData.setOnClickListener {
                getClearView()
            }
        }
        createEvents()
    }

    init {
        presenter.attachView(this)
    }

    private fun initThemeList() {
        arrayTheme.add(
            ThemeEvent(
                1,
                "Все",
                "https://rateme-social.ru/api/events/icons/all_theme.png"
            )
        )
        arrayTheme.add(ThemeEvent(2, "It", "https://rateme-social.ru/api/events/icons/it.png"))
        arrayTheme.add(
            ThemeEvent(
                3,
                "Спорт",
                "https://rateme-social.ru/api/events/icons/sports.png"
            )
        )
        arrayTheme.add(
            ThemeEvent(
                4,
                "Кино",
                "https://rateme-social.ru/api/events/icons/movies.png"
            )
        )
        arrayTheme.add(ThemeEvent(5, "Юмор", "https://rateme-social.ru/api/events/icons/humor.png"))
        arrayTheme.add(
            ThemeEvent(
                6,
                "Другое",
                "https://rateme-social.ru/api/events/icons/other.png"
            )
        )
    }

    private fun createEvents() {
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
                else -> {
                    presenter.responseCreateEvents(
                        preferencesManager.getString(Constants.USER_ID),
                        binding.editTextNameEvents.text.toString(),
                        binding.editTextDescEvents.text.toString(),
                        binding.textDateCreateEvents.text.toString(),
                        binding.textTimeCreateEvents.text.toString(),
                        binding.editTextTheme.text.toString(),
                        getBytes(inp!!)!!
                    )
                }
            }
        }
    }

    @Deprecated("Deprecated in Java")
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

    private fun getBytes(inp: InputStream): ByteArray? {
        val byteBuff = ByteArrayOutputStream()
        val buffSize = 2048
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

    override fun showProgress(show: Boolean) {
        if (show)
            progressBar =
                ProgressDialog.show(requireContext(), "Загрузка", "Подождите пожалуйста...", false)
        else progressBar.dismiss()
    }

    override fun noConnection() {
        progressBar.dismiss()
        Toast.makeText(requireContext(), "Проверьте подключение интернета.", Toast.LENGTH_SHORT)
            .show()
    }
}