package com.onitsura12.appgallery.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.onitsura12.appgallery.R
import com.onitsura12.appgallery.databinding.BottomSheetItemBinding
import com.onitsura12.appgallery.model.ImageModel
import com.squareup.picasso.Picasso

class GalleryAdapter(file: List<ImageModel>) : RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {

    private var imageList: MutableList<ImageModel> = file as MutableList<ImageModel>


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = BottomSheetItemBinding.bind(itemView)
        fun bind(image: ImageModel) {
            binding.apply {
                Picasso.get().load(image.path).placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_background).centerCrop().fit().into(imageView)


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


}