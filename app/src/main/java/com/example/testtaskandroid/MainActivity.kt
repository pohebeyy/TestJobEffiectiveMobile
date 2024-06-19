package com.example.testtaskandroid

import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.text.Spanned
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testtaskandroid.Adapters.MyAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var gson: Gson

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (applicationContext as MyApplication).appComponent.inject(this)
        setContentView(R.layout.activity_main)

        val json = """{
            "offers": [
                {"id": 1, "title": "Die Antwoord", "town": "Будапешт", "price": {"value": 5000}},
                {"id": 2, "title": "Socrat&Lera", "town": "Санкт-Петербург", "price": {"value": 1999}},
                {"id": 3, "title": "Лампабикт", "town": "Москва", "price": {"value": 2390}}
            ]
        }"""

        val offers = gson.fromJson(json, OffersResponse::class.java).offers.map {
            Offer(it.id, it.title, it.town, it.price.value)
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = MyAdapter(offers)

        val editText1: EditText = findViewById(R.id.editText1)
        val editText2: EditText = findViewById(R.id.editText2)


        val russianFilter = RussianInputFilter()
        editText1.filters = arrayOf(russianFilter)
        editText2.filters = arrayOf(russianFilter)

        val touchListener = View.OnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                showBottomSheetDialog()
                return@OnTouchListener true
            }
            false
        }

        editText1.setOnTouchListener(touchListener)
        editText2.setOnTouchListener(touchListener)
    }

    private fun showBottomSheetDialog() {
        val bottomSheetDialog = BottomSheetDialog(this)
        val view = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_dialog, null)
        bottomSheetDialog.setContentView(view)

        val bottomSheetEditText1: EditText = view.findViewById(R.id.bottomSheetEditTextDialog)
        val bottomSheetEditText2: EditText = view.findViewById(R.id.editText2Dialog)

        val editText1: EditText = findViewById(R.id.editText1)
        val editText2: EditText = findViewById(R.id.editText2)


        bottomSheetEditText1.setText(editText1.text.toString())
        bottomSheetEditText2.setText(editText2.text.toString())

        bottomSheetEditText1.addTextChangedListener {
            editText1.setText(it.toString())
        }

        bottomSheetEditText2.addTextChangedListener {
            editText2.setText(it.toString())
        }

        val hard: ImageView = view.findViewById(R.id.Hard)
        hard.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
        val kuda: ImageView = view.findViewById(R.id.Kuda)
        kuda.setOnClickListener {
            editText1.setText("Москва")
            editText2.setText("Турция")
            bottomSheetEditText1.setText("Москва")
            bottomSheetEditText2.setText("Турция")
        }
        val stambul: ImageView = view.findViewById(R.id.stambul)
        stambul.setOnClickListener {
            editText1.setText("Москва")
            editText2.setText("Стамбул")
            bottomSheetEditText1.setText("Москва")
            bottomSheetEditText2.setText("Стамбул")
        }
        val sochi: ImageView = view.findViewById(R.id.sochi)
        sochi.setOnClickListener {
            editText1.setText("Москва")
            editText2.setText("Сочи")
            bottomSheetEditText1.setText("Москва")
            bottomSheetEditText2.setText("Сочи")
        }
        val puhket: ImageView = view.findViewById(R.id.puhket)
        puhket.setOnClickListener {
            editText1.setText("Москва")
            editText2.setText("Пхукет")
            bottomSheetEditText1.setText("Москва")
            bottomSheetEditText2.setText("Пхукет")
        }

        val hot: ImageView = view.findViewById(R.id.hot)
        hot.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
        val weekend: ImageView = view.findViewById(R.id.weekend)
        weekend.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
        val search: ImageView = view.findViewById(R.id.serch)
        search.setOnClickListener {
            val intent = Intent(this@MainActivity, SearchActivity::class.java)
            intent.putExtra("bottomSheetText1", bottomSheetEditText1.text.toString())
            intent.putExtra("bottomSheetText2", bottomSheetEditText2.text.toString())
            startActivity(intent)
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.show()
    }

    private inner class RussianInputFilter : InputFilter {
        override fun filter(
            source: CharSequence?,
            start: Int,
            end: Int,
            dest: Spanned?,
            dstart: Int,
            dend: Int
        ): CharSequence? {
            if (source == null) return null

            val regex = Regex("[А-Яа-яЁё ]+")

            val filtered = source.filter { it.toString().matches(regex) }
            return if (filtered.length == source.length) null else filtered
        }
    }

    data class OffersResponse(val offers: List<OfferJson>)
    data class OfferJson(val id: Int, val title: String, val town: String, val price: PriceJson)
    data class PriceJson(val value: Int)
    data class Offer(val id: Int, val title: String, val town: String, val price: Int)
}
