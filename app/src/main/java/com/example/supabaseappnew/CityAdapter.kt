package com.example.supabaseappnew

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CityAdapter(private val cities:List<City>):RecyclerView.Adapter<CityAdapter.CityViewHolder>() {

    private var selectedPosition = -1

    class CityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val cityName: TextView = itemView.findViewById(R.id.cityname)
        val stateName: TextView = itemView.findViewById(R.id.statename)
        val cityNo: TextView = itemView.findViewById(R.id.cityno)
        private val actionButtons = itemView.findViewById<LinearLayout>(R.id.action_buttons)
        private val editButton = itemView.findViewById<FloatingActionButton>(R.id.editbtn)
        private val deleteButton = itemView.findViewById<FloatingActionButton>(R.id.deletebtn)
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

        //holder.bind(city)

        val bg = if (position == selectedPosition){
            R.drawable.bg_item_selected
        } else{
            R.drawable.bg_item_unselected
        }
        holder.itemView.findViewById<LinearLayout>(R.id.item_container).setBackgroundResource(bg)

        val actionButtons = holder.itemView.findViewById<LinearLayout>(R.id.action_buttons)

        if (position == selectedPosition) {
            actionButtons.visibility = View.VISIBLE
        } else {
            actionButtons.visibility = View.GONE
        }

        holder.itemView.setOnLongClickListener{
            selectedPosition = if (selectedPosition == position) -1 else position
            notifyDataSetChanged()
            true
        }

        val editBtn = holder.itemView.findViewById<FloatingActionButton>(R.id.editbtn)
        val deleteBtn = holder.itemView.findViewById<FloatingActionButton>(R.id.deletebtn)

        editBtn.setOnClickListener {
             // you'll pass this as a lambda from activity
        }

        deleteBtn.setOnClickListener {
             // you'll pass this too
        }
    }

    override fun getItemCount(): Int = cities.size

}