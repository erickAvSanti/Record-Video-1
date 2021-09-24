package com.karlituxd.recordvideo1

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.VideoView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val startForResult =
            this@MainActivity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                Log.e("MY_TAG", "on result")
                if (result.resultCode == Activity.RESULT_OK) {
                    Log.e("MY_TAG", "uri = " + result.data?.data?.toString())
                    result.data?.data?.also { uri ->
                        findViewById<VideoView>(R.id.my_video).apply {
                            setVideoURI(uri)
                            start()
                        }

                    }
                }
            }
        if(packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)){

            findViewById<Button>(R.id.my_button).setOnClickListener {
                Intent(MediaStore.ACTION_VIDEO_CAPTURE).also {
                        takePictureIntent ->
                    startForResult.launch(takePictureIntent)
                }
            }
        }else{
            findViewById<Button>(R.id.my_button).isEnabled = false
        }
    }
}