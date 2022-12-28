package com.onitsura12.appgallery.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.onitsura12.appgallery.R
import com.onitsura12.appgallery.databinding.BottomSheetItemBinding
import com.onitsura12.appgallery.diffutil.GalleryDiffUtilCallback
import com.onitsura12.appgallery.model.ImageModel
import com.squareup.picasso.Picasso


class GalleryAdapter : RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {

    private val imageList: MutableList<ImageModel> = ArrayList()
    private var mListener: OnListItemClickListener? = null


    fun setListener(listener: OnListItemClickListener) {
        mListener = listener
    }

    fun setData(data: List<ImageModel>) {
        val diffCallback = GalleryDiffUtilCallback(imageList, data)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        diffResult.dispatchUpdatesTo(this)
        imageList.clear()
        imageList.addAll(data)


    }


    class ViewHolder(itemView: View, listener: OnListItemClickListener) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private val binding = BottomSheetItemBinding.bind(itemView)
        private val mListener = listener


        fun bind(image: ImageModel) {
            binding.apply {

                Picasso.get()
                    .load(image.path)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_background)
                    .centerCrop()
                    .fit()
                    .into(imageView)
                when (image.isChecked) {
                    true -> checkMark.visibility = View.VISIBLE
                    false -> checkMark.visibility = View.GONE
                }

                itemView.setOnClickListener(this@ViewHolder)
            }

        }

        override fun onClick(view: View?) {
            mListener.onClick(view = view!!, position = bindingAdapterPosition)
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.bottom_sheet_item, parent, false)
        return ViewHolder(view, mListener!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(image = imageList[position])
    }

    override fun getItemCount(): Int {
        return imageList.size
    }


}