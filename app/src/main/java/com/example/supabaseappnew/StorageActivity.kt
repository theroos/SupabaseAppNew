package com.example.supabaseappnew

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import io.github.jan.supabase.createSupabaseClient
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.launch

class StorageActivity : AppCompatActivity() {


    private lateinit var supabase1: SupabaseClient
    private lateinit var storageRecyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_storage)

        storageRecyclerView = findViewById(R.id.media_recyclerview)

         supabase1 = createSupabaseClient(
            supabaseUrl = "https://aaukmjyjnmgsbgrtwzho.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImFhdWttanlqbm1nc2JncnR3emhvIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzY2OTY4MTMsImV4cCI6MjA1MjI3MjgxM30.R6Y-P3L2LAwf9UtmolyRI2tUfwO0zpQvlT6HARO8r6Y"
        )
        {
            install(io.github.jan.supabase.storage.Storage)
            install(Postgrest)
        }
        
        fetchImages()



    }

    private fun fetchImages(){
        lifecycleScope.launch {
            try{
                val imagelist = supabase1.storage.from("images").list()
                val urls = imagelist.map { supabase1.storage.from("images").publicUrl(it.name)}
                storageRecyclerView.layoutManager = GridLayoutManager(this@StorageActivity, 2)
                storageRecyclerView.adapter = ImageAdapter(urls)
                Toast.makeText(this@StorageActivity, "Adapter set with ${urls.size} images", Toast.LENGTH_SHORT).show()
                //Log.d("AdapterCheck", "Adapter set with ${urls.size} images")
                Toast.makeText(this@StorageActivity, "Fetched: ${imagelist.size}", Toast.LENGTH_SHORT).show()
                //Log.d("ImageList", "Fetched: ${imagelist.size}")
            } catch (e: Exception){
                Toast.makeText(this@StorageActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}