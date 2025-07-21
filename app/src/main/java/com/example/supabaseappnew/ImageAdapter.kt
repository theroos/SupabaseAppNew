package com.example.supabaseappnew

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import io.ktor.http.Url
import com.bumptech.glide.Glide

class ImageAdapter(private val urls: List<String>): RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {
    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.image_thumbnail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image,parent,false)
        return ImageViewHolder(view)
    }

    override fun getItemCount(): Int = urls.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val url = urls[position]
        //Toast.makeText(holder.imageView.context, "Binding image at $position: ${urls[position]}", Toast.LENGTH_SHORT).show()
        //Log.d("ImageAdapter", "Binding image at $position: ${urls[position]}")
        Glide.with(holder.imageView.context).load(urls[position]).into(holder.imageView)

        holder.imageView.setOnClickListener {
            val fullImageFragment = FullImageFragment.newInstance(url)

        }
    }

}