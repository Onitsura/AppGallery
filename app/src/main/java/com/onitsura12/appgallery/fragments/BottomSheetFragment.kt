package com.onitsura12.appgallery.fragments


import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
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
import com.onitsura12.appgallery.recyclerview.OnListItemClickListener


class BottomSheetFragment : BottomSheetDialogFragment() {


    private lateinit var binding: BottomSheetFragmentBinding
    private val adapter = GalleryAdapter()
    private lateinit var viewModel: BottomSheetViewModel


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetFragmentBinding.inflate(inflater)
        viewModel = BottomSheetViewModel(contentResolver = requireActivity().contentResolver)
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
            //Setting up listener to adapter
            val listener = object : OnListItemClickListener {
                override fun onClick(view: View, position: Int) {
                    val item = viewModel.imagesList.value!![position]
                    viewModel.markItem(item)

                }
            }
            adapter.setListener(listener = listener)

            //Checking permissions
            permissionGranted()

            //Setting up RecyclerView
            viewModel.imagesList.observe(viewLifecycleOwner) {
                val images: List<ImageModel> = it
                rcView.layoutManager = GridLayoutManager(requireContext(), 2)
                adapter.setData(images)
                rcView.adapter = adapter
            }
        }
    }


    private fun permissionGranted() {
        val activity = requireActivity()
        if (ContextCompat.checkSelfPermission(
                activity.applicationContext, Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(
                activity, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1
            )
        }
    }
}