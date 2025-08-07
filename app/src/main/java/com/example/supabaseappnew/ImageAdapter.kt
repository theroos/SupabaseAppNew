package com.example.supabaseappnew

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import io.ktor.http.Url
import com.bumptech.glide.Glide

class ImageAdapter(private val urls: List<String>,private val fragmentManager: FragmentManager): RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {


    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.image_thumbnail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image,parent,false)
        return ImageViewHolder(view)
    }



    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val url = urls[position]
        //Toast.makeText(holder.imageView.context, "Binding image at $position: ${urls[position]}", Toast.LENGTH_SHORT).show()
        //Log.d("ImageAdapter", "Binding image at $position: ${urls[position]}")
        Glide.with(holder.imageView.context).load(urls[position]).into(holder.imageView)

        holder.imageView.setOnClickListener {
            val clickedImageUrl: String = urls[position]
            //val fullImageFragment = FullImageFragment.newInstance(urls, position)
            //fragmentManager.beginTransaction().add(R.id.fullScreenImageView, fullImageFragment).addToBackStack(null).commit()
            val fullImageFragment = FullImageFragment.newInstance(urls, position).apply {
                onImageDeleted = {
                    onImageDeleted?.invoke()
                    (parentFragment as? DialogFragment)?.dismiss() // ✅ Then close the dialog
                } // 🔁 calls fetchImages() in activity
            }

            fullImageFragment.show(fragmentManager, "full_image")

        }
    }

    override fun getItemCount(): Int = urls.size

}