package com.example.supabaseappnew

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var recylerview: RecyclerView
    private lateinit var adapter: CityAdapter
    private lateinit var supabase: SupabaseClient

    private val rotateOpen: Animation by lazy { AnimationUtils.loadAnimation(this,R.anim.rotate_open_animation) }
    private val rotateClose: Animation by lazy { AnimationUtils.loadAnimation(this,R.anim.rotate_close_animation) }
    private val fromBottom: Animation by lazy { AnimationUtils.loadAnimation(this,R.anim.from_bottom_animation) }
    private val toBottom: Animation by lazy { AnimationUtils.loadAnimation(this,R.anim.to_bottom_animation) }
    private val fabback: View by lazy{ findViewById(R.id.fab_background)}
    private val progressoverlay: View by lazy {findViewById(R.id.progbar_overlay)}
    private val add_btn: FloatingActionButton by lazy {findViewById(R.id.add_btn) }
    private val refresh_btn: FloatingActionButton by lazy {findViewById(R.id.refresh_btn) }
    private val edit_btn: FloatingActionButton by lazy {findViewById(R.id.edit_btn) }
    private val storage_btn: FloatingActionButton by lazy {findViewById(R.id.storageactivity_btn) }
    private var clicked = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        recylerview = findViewById(R.id.recylerview)
        recylerview.layoutManager = LinearLayoutManager(this)


        add_btn.setOnClickListener{
            addclicked()
        }
        refresh_btn.setOnClickListener{
            fetchcities()
        }
        edit_btn.setOnClickListener {
            //Toast.makeText(this,"Edit button clicked",Toast.LENGTH_LONG).show()
            editdata()
        }
        storage_btn.setOnClickListener {
            val i = Intent(this,StorageActivity::class.java)
            startActivity(i)
        }

        fabback.setOnClickListener{
            addclicked()
        }

        supabase = createSupabaseClient(
            supabaseUrl = "https://aaukmjyjnmgsbgrtwzho.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImFhdWttanlqbm1nc2JncnR3emhvIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzY2OTY4MTMsImV4cCI6MjA1MjI3MjgxM30.R6Y-P3L2LAwf9UtmolyRI2tUfwO0zpQvlT6HARO8r6Y"
        ) {
            install(Auth)
            install(Postgrest)
            //install other modules
        }

        fetchcities()

    }

    private fun editdata() {
        adddatapopup()
        /*val city = City(city = "Guwahati", state = "Assam", number = 8)

        lifecycleScope.launch {
            try{
                supabase.from("cities").insert(city)
                fetchcities()
            } catch (e: Exception){
                Log.e("SupabaseError", "Error: ${e.localizedMessage}", e)
                Toast.makeText(this@MainActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }*/
    }


    private fun fetchcities() {

        progressoverlay.visibility = View.VISIBLE

        lifecycleScope.launch{
            /*try {
                val result = supabase.postgrest["cities"].select().decodeList<City>()
                Log.d("Supabase", "Fetched ${result.size} items")
                Toast.makeText(this@MainActivity, "Fetched ${result.size} items", Toast.LENGTH_LONG).show()
                recylerview.adapter = CityAdapter(result)
            } catch (e:Exception){
                Log.e("SupabaseError", "Error: ${e.localizedMessage}", e)
                Toast.makeText(this@MainActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }*/

            try {
                val response = supabase.postgrest["cities"].select()

                // Step 1: Log raw response to see actual field names
                Log.d("SupabaseRaw", "JSON: $response")

                val result = response.decodeList<City>()

                //Toast.makeText(this@MainActivity, "Fetched ${result.size} items", Toast.LENGTH_LONG).show()

                // Step 2: Set adapter with real data
                recylerview.adapter = CityAdapter(result, ::showdeleteconfirmation, ::showeditdatapopup)

            } catch (e: Exception) {
                Log.e("SupabaseError", "Error: ${e.localizedMessage}", e)
                Toast.makeText(this@MainActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }finally {
                progressoverlay.visibility = View.GONE
            }
        }
    }

    private fun addclicked() {
        setvisibility(clicked)
        setAnimation(clicked)
        if(!clicked){
            clicked = true
        } else {clicked = false}
    }

    private fun setvisibility(clicked: Boolean){
        if(!clicked){
            refresh_btn.visibility = View.VISIBLE
            edit_btn.visibility = View.VISIBLE
            storage_btn.visibility = View.VISIBLE
            fabback.visibility = View.VISIBLE
        } else{
            refresh_btn.visibility = View.INVISIBLE
            edit_btn.visibility = View.INVISIBLE
            storage_btn.visibility = View.INVISIBLE
            fabback.visibility = View.GONE
        }
    }

    private fun setAnimation(clicked: Boolean){
        if(!clicked){
            add_btn.startAnimation(rotateOpen)
            refresh_btn.startAnimation(fromBottom)
            edit_btn.startAnimation(fromBottom)
            storage_btn.startAnimation(fromBottom)
        }else{
            add_btn.startAnimation(rotateClose)
            refresh_btn.startAnimation(toBottom)
            edit_btn.startAnimation(toBottom)
            storage_btn.startAnimation(toBottom)
        }
    }

    private fun adddatapopup(){
        val inflater = layoutInflater
        val popupView = inflater.inflate(R.layout.add_data_popup,null)

        val widthinDP = 300
        val scale = resources.displayMetrics.density
        val widthDp = (widthinDP * scale + 0.5f).toInt()

        val popupWindow = PopupWindow(popupView,widthDp,LinearLayout.LayoutParams.WRAP_CONTENT,true)

        popupWindow.showAtLocation(popupView, Gravity.CENTER,0,0)

        val button_addpopup = popupView.findViewById<Button>(R.id.button_addpopup)
        val city_ent = popupView.findViewById<EditText>(R.id.city_ent)
        val state_ent = popupView.findViewById<EditText>(R.id.state_ent)
        val no_ent = popupView.findViewById<EditText>(R.id.no_ent)

        button_addpopup.setOnClickListener{
            val city = city_ent.text.toString()
            val state = state_ent.text.toString()
            val number = no_ent.text.toString().toInt()

            if(city.isNotEmpty() && state.isNotEmpty() && number != null){
                insertData(city,state,number)
                fetchcities()
                popupWindow.dismiss()
            } else{
                Toast.makeText(this,"Please fill all required fields",Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun showeditdatapopup(city: City){

        val widthinDP = 300
        val scale = resources.displayMetrics.density
        val widthDp = (widthinDP * scale + 0.5f).toInt()

        val popupview = layoutInflater.inflate(R.layout.edit_popup,null)
        val popupwindow = PopupWindow(popupview,widthDp,ViewGroup.LayoutParams.WRAP_CONTENT,true)

        val EditCity = popupview.findViewById<EditText>(R.id.edit_city)
        val EditState = popupview.findViewById<EditText>(R.id.edit_state)
        val EditNumber = popupview.findViewById<EditText>(R.id.edit_no)
        val SaveBtn = popupview.findViewById<Button>(R.id.editsave_btn)

        EditCity.setText(city.city)
        EditState.setText(city.state)
        EditNumber.setText(city.number.toString())

        SaveBtn.setOnClickListener{
            val updatedcity = City(city = EditCity.text.toString(),
                                   state = EditState.text.toString(),
                                   number = EditNumber.text.toString().toIntOrNull() ?: 0)

            updateData(city,updatedcity)
            popupwindow.dismiss()
        }

        popupwindow.elevation = 10f
        popupwindow.showAtLocation(popupview,Gravity.CENTER,0,0)





    }

    private fun updateData(oldcity: City, updatedcity: City) {
        lifecycleScope.launch{

            val isChanged = oldcity.number != updatedcity.number
            if (isChanged && !isNumberUnique(updatedcity.number)){
                Toast.makeText(this@MainActivity,"This number already exists",Toast.LENGTH_SHORT).show()
                return@launch
            }
            try{
                supabase.from("cities").update(updatedcity){filter { eq("name",oldcity.city) }}

                Toast.makeText(this@MainActivity,"Updated ${updatedcity.city}",Toast.LENGTH_LONG).show()
                fetchcities()
            }catch (e: Exception){
                Toast.makeText(this@MainActivity,"Error: ${e.message}",Toast.LENGTH_LONG).show()
        }
        }
    }

    private fun insertData(city: String, state: String, number: Int){
        val city = City(city = city, state = state, number = number)

        lifecycleScope.launch {
            val isUnique = isNumberUnique(city.number)
            if(!isUnique){
                Toast.makeText(this@MainActivity,"This number already exists",Toast.LENGTH_SHORT).show()
                return@launch
            }
            try{
                supabase.from("cities").insert(city)
                fetchcities()
            } catch (e: Exception){
                Log.e("SupabaseError", "Error: ${e.localizedMessage}", e)
                Toast.makeText(this@MainActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun showdeleteconfirmation(city: City){
        val dialog = AlertDialog.Builder(this).setTitle("Delete ${city.city}?")
            .setMessage("Are you sure you want to delete ${city.city}?").setPositiveButton("Delete"){_,_ ->
                deleteData(city)
            }.setNegativeButton("Cancel",null).create()
        dialog.show()
    }

    private fun deleteData(city: City){
        lifecycleScope.launch {
            try{
                //Toast.makeText(this@MainActivity,"Trying to delete ${city.city} with the number ${city.number}",Toast.LENGTH_SHORT).show()

                val response = supabase.from("cities").delete{filter { eq("no",city.number) }}
                //Log.d("DeleteDebug", "Delete response: $response")
                Toast.makeText(this@MainActivity,"Deleted ${city.city}",Toast.LENGTH_LONG).show()
                fetchcities()
            }catch (e: Exception){
                //Log.e("DeleteError", "Unable to delete ${city.city}", e)
                Toast.makeText(this@MainActivity,"Error: ${e.message}",Toast.LENGTH_LONG).show()
            }
        }
    }

    private suspend fun isNumberUnique(number: Int): Boolean {
        return try{
            val result = supabase.from("cities").select { filter{eq("no",number)} }.decodeList<City>()
            result.isEmpty()
        } catch (e: Exception){
            Log.e("SupabaseError", "Error: ${e.localizedMessage}", e)
            false
        }
    }

}