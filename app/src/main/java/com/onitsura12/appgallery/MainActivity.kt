package com.onitsura12.appgallery

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.onitsura12.appgallery.databinding.ActivityMainBinding
import com.onitsura12.appgallery.fragments.BottomSheetFragment


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)





        binding.apply {
            buttonCam.setOnClickListener {
                initCam()
            }
            buttonGallery.setOnClickListener {
                initBottomSheet()
            }
        }
    }

    private fun initCam() {

    }


    private fun initBottomSheet() {
        val bottomSheet = BottomSheetFragment()
        bottomSheet.show(supportFragmentManager, "Gallery")
    }
}