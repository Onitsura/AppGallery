package com.onitsura12.appgallery.fragments


import android.Manifest
import android.content.ContentUris
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.onitsura12.appgallery.databinding.BottomSheetFragmentBinding
import com.onitsura12.appgallery.model.ImageModel
import com.onitsura12.appgallery.recyclerview.GalleryAdapter


class BottomSheetFragment : BottomSheetDialogFragment() {


    private lateinit var binding: BottomSheetFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetFragmentBinding.inflate(inflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun init() {
        binding.apply {
            val activity = requireActivity()
            if (ContextCompat.checkSelfPermission(
                    activity.applicationContext, Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_DENIED
            ) {
                ActivityCompat.requestPermissions(
                    activity, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1
                )
            } else {
                val images: List<ImageModel> = getImages().reversed()
                rcView.layoutManager = GridLayoutManager(requireContext(), 2)
                val adapter = GalleryAdapter(file = images)
                rcView.adapter = adapter

            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun getImages(): List<ImageModel> {

        val list: ArrayList<ImageModel> = ArrayList()

        val collection: Uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
        } else {
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }

        val projection: Array<String> = arrayOf(MediaStore.Images.Media._ID)

        requireActivity().contentResolver.query(
            collection, projection, null, null
        ).use { cursor ->
            val idColumn: Int = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media._ID)


            while (cursor.moveToNext()) {
                val id: Long = cursor.getLong(idColumn)
                val contentUri: Uri = ContentUris.withAppendedId(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id
                )

                list.add(ImageModel(contentUri))
            }
            cursor.close()
        }

        return list
    }

}