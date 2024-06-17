package com.example.testtaskandroid

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testtaskandroid.jsonConvertor.TicketX
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import java.io.IOException

class AllBilets : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TicketsAdapter
    private lateinit var textView1: TextView
    private lateinit var textView2: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_bilets)

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        textView1 = findViewById(R.id.textView1)
        textView2 = findViewById(R.id.textView2)

        // Получение данных, переданных из SearchActivity
        val editText1Text = intent.getStringExtra("editText1")
        val editText2Text = intent.getStringExtra("editText2")

        // Установка полученных данных в TextView
        textView1.text = editText1Text
        textView2.text = editText2Text
        val back:ImageView = findViewById(R.id.back)
        back.setOnClickListener {
            onBackPressed()
        }

        try {
            // Загрузка JSON из файла assets
            val jsonString = loadJSONFromAsset("tickets.json")

            // Парсинг JSON
            val jsonObject = JSONObject(jsonString)
            val ticketsArray = jsonObject.getJSONArray("tickets")
            val tickets: List<TicketX> = Gson().fromJson(ticketsArray.toString(), object : TypeToken<List<TicketX>>() {}.type)

            // Установка адаптера для RecyclerView
            adapter = TicketsAdapter(tickets)
            recyclerView.adapter = adapter
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun loadJSONFromAsset(fileName: String): String {
        var json = ""
        try {
            val inputStream = assets.open(fileName)
            json = inputStream.bufferedReader().use { it.readText() }
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
        return json
    }
}
