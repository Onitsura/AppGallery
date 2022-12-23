package com.onitsura12.appgallery.fragments


import android.Manifest
import android.app.ActivityManager
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.recreate
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.onitsura12.appgallery.databinding.BottomSheetFragmentBinding
import com.onitsura12.appgallery.recyclerview.GalleryAdapter
import java.util.*


private const val MY_PERMISSIONS_REQUEST = 1234
private val PERMISSIONS =
    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)


class BottomSheetFragment : BottomSheetDialogFragment() {


    private lateinit var binding: BottomSheetFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
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
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && isPermissions()) {
                requestPermissions(PERMISSIONS, MY_PERMISSIONS_REQUEST)
                return
            }


            val files = getCameraImages().reversed()

            //RecyclerView
            rcView.layoutManager = GridLayoutManager(layoutInflater.context, 2)
            val adapter = GalleryAdapter(file = files)
            adapter.update(files as MutableList<String>)
            rcView.adapter = adapter


        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == MY_PERMISSIONS_REQUEST && grantResults.isNotEmpty()) {
            if (isPermissions()) {
                (Objects.requireNonNull(
                    requireActivity().applicationContext.getSystemService(
                        Context.ACTIVITY_SERVICE
                    )
                ) as ActivityManager).clearApplicationUserData()
                recreate(requireActivity())
            }
        }
    }


    private fun isPermissions(): Boolean {
        PERMISSIONS.forEach {
            if (ActivityCompat.checkSelfPermission(
                    requireActivity(),
                    it
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return true
            }
        }
        return false
    }


    private fun getCameraImages(): List<String> {
        val uri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val cursor: Cursor?
        val listOfAllImages = ArrayList<String>()
        var absolutePathOfImage: String?

        val projection =
            arrayOf(MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME)

        cursor = requireActivity().contentResolver.query(
            uri, projection, null,
            null, null
        )

        val column_index_data: Int = cursor!!.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
        cursor
            .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data)
            listOfAllImages.add(absolutePathOfImage)
        }
        cursor.close()
        return listOfAllImages

    }


}