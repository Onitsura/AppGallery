package com.onitsura12.appgallery.fragments


import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.onitsura12.appgallery.databinding.BottomSheetFragmentBinding
import com.onitsura12.appgallery.recyclerview.GalleryAdapter


class BottomSheetFragment : BottomSheetDialogFragment() {


    private lateinit var binding: BottomSheetFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetFragmentBinding.inflate(inflater)
        binding.apply {


            val files = getCameraImages().reversed()

            //RecyclerView
            rcView.layoutManager = GridLayoutManager(layoutInflater.context, 2)
            val adapter = GalleryAdapter(file = files)
            rcView.adapter = adapter


        }



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }


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
                val files = getCameraImages().reversed()

                //RecyclerView
                rcView.layoutManager = GridLayoutManager(layoutInflater.context, 2)
                val adapter = GalleryAdapter(file = files)
                adapter.update(files as MutableList<String>)
                rcView.adapter = adapter
            }


        }
    }


    private fun getCameraImages(): List<String> {
        val uri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val listOfAllImages = ArrayList<String>()
        val projection =
            arrayOf(MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME)

        val cursor = requireActivity().contentResolver.query(
            uri, projection, null, null, null
        )

        val columnIndexData: Int = cursor!!.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
        cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
        while (cursor.moveToNext()) {
            val absolutePathOfImage = cursor.getString(columnIndexData)
            listOfAllImages.add(absolutePathOfImage)
        }
        cursor.close()
        return listOfAllImages

    }


}