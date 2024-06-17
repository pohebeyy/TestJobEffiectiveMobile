package com.example.testtaskandroid

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStreamReader
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class SearchActivity : AppCompatActivity() {

    private lateinit var departureDateEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val editText1 = findViewById<EditText>(R.id.bottomSheetEditTextSearch)
        val editText2 = findViewById<EditText>(R.id.editText2Search)
        val swap = findViewById<ImageView>(R.id.swap)
        val clear = findViewById<ImageView>(R.id.clear)
        val back = findViewById<ImageView>(R.id.back)
        val giveAll = findViewById<ImageView>(R.id.givaAll)

        // Swap button functionality
        swap.setOnClickListener {
            val tempText = editText1.text.toString()
            editText1.setText(editText2.text.toString())
            editText2.setText(tempText)
        }

        giveAll.setOnClickListener {
            val intent = Intent(this@SearchActivity, AllBilets::class.java)
            intent.putExtra("editText1", editText1.text.toString())
            intent.putExtra("editText2", editText2.text.toString())
            startActivity(intent)
        }
        back.setOnClickListener {
            onBackPressed()
        }
        clear.setOnClickListener {
            editText1.setText("")
            editText2.setText("")
        }

        departureDateEditText = findViewById(R.id.editData)
        val currentDate = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        departureDateEditText.setText(dateFormat.format(currentDate))

        departureDateEditText.setOnClickListener {
            showDatePickerDialog()
        }
        departureDateEditText = findViewById(R.id.backAir)
        val currentDate1 = Calendar.getInstance().time
        val dateFormat1 = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        departureDateEditText.setText(dateFormat1.format(currentDate1))

        departureDateEditText.setOnClickListener {
            showDatePickerDialog()
        }
        val bottomSheetText1 = intent.getStringExtra("bottomSheetText1")
        val bottomSheetText2 = intent.getStringExtra("bottomSheetText2")

        editText1.setText(bottomSheetText1)
        editText2.setText(bottomSheetText2)

        val linearLayoutContainer = findViewById<LinearLayout>(R.id.linearLayoutContainer)
        val airlineLogos = arrayOf(
            R.drawable.red,
            R.drawable.blue,
            R.drawable.white
        )

        try {
            val inputStream = assets.open("tickets_offers.json")
            val reader = InputStreamReader(inputStream)
            val ticketType = object : TypeToken<TicketsResponse>() {}.type
            val ticketsResponse: TicketsResponse = Gson().fromJson(reader, ticketType)
            reader.close()

            ticketsResponse.tickets_offers.forEachIndexed { index, ticketOffer ->
                val view = layoutInflater.inflate(R.layout.item_ticket, linearLayoutContainer, false)

                val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
                val tvPrice = view.findViewById<TextView>(R.id.tvPrice)
                val time = view.findViewById<TextView>(R.id.time)
                val ivAirlineLogo = view.findViewById<ImageView>(R.id.ivAirlineLogo)

                tvTitle.text = ticketOffer.title
                tvPrice.text = "${ticketOffer.price.value} руб."
                time.text = ticketOffer.time_range.joinToString(", ")

                if (index < airlineLogos.size) {
                    ivAirlineLogo.setImageResource(airlineLogos[index])
                }
                linearLayoutContainer.addView(view)
            }

        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Error loading data", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, dayOfMonth)
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                departureDateEditText.setText(dateFormat.format(selectedDate.time))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }
}

data class TicketOffer(
    val id: Int,
    val title: String,
    val time_range: List<String>,
    val price: Price
)

data class Price(
    val value: Int
)

data class TicketsResponse(
    val tickets_offers: List<TicketOffer>
)
