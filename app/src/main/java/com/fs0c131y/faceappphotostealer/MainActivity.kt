package com.fs0c131y.faceappphotostealer

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.StrictMode
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
        const val RC_STORAGE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        disableDeathOnFileUriExposure()
        scanFaceAppFolder()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    @AfterPermissionGranted(RC_STORAGE)
    private fun scanFaceAppFolder() {
        val perms = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
        if (EasyPermissions.hasPermissions(this, *perms)) {
            val faceAppPhotos = getFaceAppPhotos()
            list_recycler_view.apply {
                // set a LinearLayoutManager to handle Android
                // RecyclerView behavior
                layoutManager = LinearLayoutManager(this@MainActivity)
                // set the custom adapter to the RecyclerView
                adapter = ListAdapter(faceAppPhotos) {
                    showImage(it)
                }
            }

            for (photo in faceAppPhotos) {
                Log.e(TAG, "name = ${photo.name}")
            }
        } else {
            EasyPermissions.requestPermissions(
                this, getString(R.string.read_storage_rationale),
                RC_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }
    }

    private fun getFaceAppPhotos(): List<FaceAppPhoto> {
        val faceAppPhotos = File("${Environment.getExternalStorageDirectory()}/FaceApp/").listFiles()
        val result = ArrayList<FaceAppPhoto>()
        for (photo in faceAppPhotos) {
            val timestamp = photo.name.split('_')[1]
            result.add(FaceAppPhoto(photo.name, convertEpoch(timestamp.split('.')[0])))
        }
        return result
    }

    private fun convertEpoch(timestamp: String): String {
        val timeLong = (timestamp.toLong() / 1000)
        val d = Date(timeLong * 1000L)
        return SimpleDateFormat("dd-MMM HH:mm", Locale.US).format(d)
    }

    private fun disableDeathOnFileUriExposure() {
        if (Build.VERSION.SDK_INT >= 24) {
            try {
                val m = StrictMode::class.java.getMethod("disableDeathOnFileUriExposure")
                m.invoke(null)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    private fun showImage(photoName: String) {
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.setDataAndType(
            Uri.parse("file://" + "${Environment.getExternalStorageDirectory()}/FaceApp/$photoName"),
            "image/*"
        )
        startActivity(intent)
    }
}
