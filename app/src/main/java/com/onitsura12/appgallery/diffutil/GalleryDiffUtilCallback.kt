package com.onitsura12.appgallery.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.onitsura12.appgallery.model.ImageModel


class GalleryDiffUtilCallback(private val oldList: List<ImageModel>, private val newList: List<ImageModel>): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.path == newItem.path
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem == newItem
    }

//    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Bundle? {
//        val oldItem = oldList[oldItemPosition]
//        val newItem = newList[newItemPosition]
//        val diffBundle = Bundle()
//
//        if (newItem.isChecked != oldItem.isChecked) {
//            diffBundle.putBoolean("Checked", newItem.isChecked)
//        }
//
//        return if (diffBundle.size() == 0) null else diffBundle
//    }
}