package com.example.testtaskandroid.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testtaskandroid.R
import com.example.testtaskandroid.jsonConvertor.TicketX
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class TicketsAdapter(private val ticketList: List<TicketX>) : RecyclerView.Adapter<TicketsAdapter.TicketViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.items_ticket, parent, false)
        return TicketViewHolder(view)
    }

    override fun onBindViewHolder(holder: TicketViewHolder, position: Int) {
        val ticket = ticketList[position]
        holder.bind(ticket)
    }

    override fun getItemCount(): Int {
        return ticketList.size
    }

    class TicketViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val priceTextView: TextView = itemView.findViewById(R.id.price)
        private val badgeTextView: TextView = itemView.findViewById(R.id.badge)
        private val badgeContainer: LinearLayout = itemView.findViewById(R.id.badge_container)
        private val otkuda: TextView = itemView.findViewById(R.id.departureAirport)
        private val kuda: TextView = itemView.findViewById(R.id.arrivalAirport)
        private val departureInfoTextView: TextView = itemView.findViewById(R.id.departure_info)
        private val arrivalInfoTextView: TextView = itemView.findViewById(R.id.arrival_info)
        private val timeTextView: TextView = itemView.findViewById(R.id.time)

        fun bind(ticket: TicketX) {
            otkuda.text = ticket.departure.airport
            kuda.text = ticket.arrival.airport
            priceTextView.text = "Цена: ${ticket.price.value} ₽"

            if (ticket.badge.isNullOrEmpty()) {
                badgeContainer.visibility = View.GONE
            } else {
                badgeContainer.visibility = View.VISIBLE
                badgeTextView.text = ticket.badge
            }

            departureInfoTextView.text = ticket.departure.date
            arrivalInfoTextView.text = ticket.arrival.date


            val flightTime = calculateFlightTime(ticket.departure.date, ticket.arrival.date)
            timeTextView.text = flightTime
        }

        private fun calculateFlightTime(departure: String, arrival: String): String {
            val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
            return try {
                val departureDate = dateFormat.parse(departure)
                val arrivalDate = dateFormat.parse(arrival)
                if (departureDate != null && arrivalDate != null) {
                    val diffInMillis = arrivalDate.time - departureDate.time
                    val hours = TimeUnit.MILLISECONDS.toHours(diffInMillis)
                    val minutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillis) % 60
                    "${hours}ч ${minutes}м"
                } else {departureDate
                    "N/A"
                }
            } catch (e: Exception) {
                e.printStackTrace()
                "N/A"
            }
        }
    }
}
