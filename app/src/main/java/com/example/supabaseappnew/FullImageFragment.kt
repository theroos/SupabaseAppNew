package com.example.supabaseappnew

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide


class FullImageFragment : DialogFragment() {


    companion object {
        private const val ARG_IMAGE_URL = "image_url"
        private const val ARG_START_POSITION = "start_position"

        fun newInstance(imageUrls: List<String>, startPosition: Int): FullImageFragment{
            val fragment = FullImageFragment()
            val args = Bundle()
            //args.putStringArrayList(ARG_IMAGE_URL, ArrayList<String>(listOf(imageUrl)))
            args.putStringArrayList(ARG_IMAGE_URL, ArrayList(imageUrls))
            args.putInt(ARG_START_POSITION, startPosition)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image_viewpager, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //val imageView = view.findViewById<ImageView>(R.id.fullScreenImageView)
        //val imageUrl = arguments?.getString(ARG_IMAGE_URL)
        val imageUrls = arguments?.getStringArrayList(ARG_IMAGE_URL) ?: emptyList<String>()
        val startPosition = arguments?.getInt(ARG_START_POSITION) ?: 0
        val viewPager = view.findViewById<ViewPager2>(R.id.viewPager2)
        //val viewPager = view.findViewById<androidx.viewpager2.widget.ViewPager2>(R.id.viewPager2)
        //Log.d("FullImageFragment", "Number of Image URLs: ${imageUrls.size}")
        Toast.makeText(this.context, "Number of Image URLs: ${imageUrls.size}", Toast.LENGTH_SHORT).show()
        viewPager.adapter = FullImagePagerAdapter(imageUrls)
        viewPager.setCurrentItem(startPosition, false)

        //Glide.with(this).load(imageUrl).into(imageView)

        /*imageView.setOnClickListener{
           dismiss()
        }*/


    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }
}