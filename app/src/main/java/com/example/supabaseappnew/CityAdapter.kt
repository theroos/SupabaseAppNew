package com.example.supabaseappnew

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CityAdapter(private val cities:List<City>):RecyclerView.Adapter<CityAdapter.CityViewHolder>() {

    class CityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val cityName: TextView = itemView.findViewById(R.id.cityname)
        val stateName: TextView = itemView.findViewById(R.id.statename)
        val cityNo: TextView = itemView.findViewById(R.id.cityno)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityAdapter.CityViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_city, parent, false)
        return CityViewHolder(view)
    }

    override fun onBindViewHolder(holder: CityAdapter.CityViewHolder, position: Int) {

        val city = cities[position]
        holder.cityName.text = city.city
        holder.stateName.text = city.state
        holder.cityNo.text = city.number.toString()
    }

    override fun getItemCount(): Int = cities.size

}