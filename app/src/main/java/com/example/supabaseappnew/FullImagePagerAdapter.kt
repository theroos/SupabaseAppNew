package com.example.supabaseappnew

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class FullImagePagerAdapter(private val imageUrls: List<String>): RecyclerView.Adapter<FullImagePagerAdapter.PagerViewHolder>() {

    inner class PagerViewHolder(view : View):RecyclerView.ViewHolder(view)
    {
        val fullimageview: ImageView = view.findViewById(R.id.fullScreenImageView)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FullImagePagerAdapter.PagerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_full_image, parent, false)
        return PagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: FullImagePagerAdapter.PagerViewHolder, position: Int) {
        Glide.with(holder.fullimageview.context)
            .load(imageUrls[position])
            .into(holder.fullimageview)
    }

    override fun getItemCount(): Int {
        return imageUrls.size
    }
}