package com.example.supabaseappnew

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FullImagePagerAdapter(
    private val imageUrls: List<String>,
    fragment: Fragment,
    private val onImageDeleted: (() -> Unit)? = null
): RecyclerView.Adapter<FullImagePagerAdapter.PagerViewHolder>() {

    private val parentFragment = fragment

    inner class PagerViewHolder(view : View):RecyclerView.ViewHolder(view)
    {
        val fullimageview: ImageView = view.findViewById(R.id.fullScreenImageView)
        val deleteimagebtn: ImageView = view.findViewById(R.id.delete_imagebtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder
    {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_full_image, parent, false)
        return PagerViewHolder(view)
    }


    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        val url = imageUrls[position]
        Log.d("ViewPager", "Loading image at $position: $url")
        //Toast.makeText(holder.fullimageview.context, "Loading image at $position: $url", Toast.LENGTH_SHORT).show()
        Glide.with(holder.fullimageview.context)
            .load(imageUrls[position])
            .into(holder.fullimageview)

        holder.deleteimagebtn.setOnClickListener {
            AlertDialog.Builder(holder.itemView.context).setTitle("Delete this image permanently?").setMessage("This action cannot be undone").setPositiveButton("Delete")
            {_, _ ->
                val url = imageUrls[position]
                val path = getFilePathFromUrl(url)

                CoroutineScope(Dispatchers.IO).launch{
                    val success = deleteImageFromSupabase(path)
                    withContext(Dispatchers.Main){
                        if(success){
                            Toast.makeText(holder.itemView.context, "Image deleted successfully", Toast.LENGTH_SHORT).show()
                            onImageDeleted?.invoke()
                            (parentFragment as? DialogFragment)?.dismiss()

                        } else{
                            Toast.makeText(holder.itemView.context, "Failed to delete image", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
                .setNegativeButton("Cancel", null).show()
        }
    }

    private fun getFilePathFromUrl(url: String): String {
        val baseUrl = "https://aaukmjyjnmgsbgrtwzho.supabase.co/storage/v1/object/public/images/"
        return url.removePrefix(baseUrl)
    }

    override fun getItemCount(): Int {
        return imageUrls.size
    }

    suspend fun deleteImageFromSupabase(path: String): Boolean {
        //Log.d("DeleteDebug", "Trying to delete: '$path'")
        //Toast.makeText(null, "Trying to delete: '$path'", Toast.LENGTH_SHORT).show()
        return try{
            val result = SupabaseClientManager.supabase.storage.from("images").delete(listOf(path))

           true
        } catch (e: Exception){
            Log.e("FullImagePagerAdapter", "Error deleting image from Supabase", e)
            false
        }
    }
}