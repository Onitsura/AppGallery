package com.onitsura12.appgallery.recyclerview

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.onitsura12.appgallery.R
import com.onitsura12.appgallery.databinding.BottomSheetItemBinding
import com.squareup.picasso.Picasso

class GalleryAdapter(file: List<String>) : RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {

    private var imageList: MutableList<String> = file as MutableList<String>


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = BottomSheetItemBinding.bind(itemView)
        fun bind(image: String) {
            binding.apply {
                uriTV.text = image
                val uri = Uri.parse(image)
                Picasso.get()
                    .load(uri)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_background)
                    .fit()
                    .into(imageGallery)
            }


        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.bottom_sheet_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(image = imageList[position])
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    fun update(newList: MutableList<String>) {
        imageList = newList
        notifyDataSetChanged()
    }
}