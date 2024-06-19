package com.example.testtaskandroid.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testtaskandroid.MainActivity
import com.example.testtaskandroid.R

data class Offer(
    val id: Int,
    val title: String,
    val town: String,
    val price: Int
)

class MyAdapter(private val offers: List<MainActivity.Offer>) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    private val imageMap = mapOf(
        1 to R.drawable.budapest,
        2 to R.drawable.piter,
        3 to R.drawable.moscow
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val offer = offers[position]
        holder.title.text = offer.title
        holder.town.text = offer.town
        holder.price.text = String.format("%,d", offer.price)  // Форматирование цены с разделением разрядов
        holder.image.setImageResource(imageMap[offer.id] ?: R.drawable.budapest)  // Загрузка изображения
    }

    override fun getItemCount(): Int {
        return offers.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.image)
        val title: TextView = itemView.findViewById(R.id.title)
        val town: TextView = itemView.findViewById(R.id.town)
        val price: TextView = itemView.findViewById(R.id.price)
    }
}
