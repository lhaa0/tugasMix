package com.rizki.androidlatihanmix_fadilarizki.fragment

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.rizki.androidlatihanmix_fadilarizki.R
import com.rizki.androidlatihanmix_fadilarizki.model.ModelUpload
import kotlinx.android.synthetic.main.fragment_upload.*
import java.io.IOException
import java.util.*

class FragmentUpload : Fragment() {

    val REQUEST_IMAGE = 10002
    val PERMISSION_REQUEST_CODE = 10003
    lateinit var filePathImage: Uri
    var value = 0.0

    lateinit var dbRef: DatabaseReference
    lateinit var fStorage: FirebaseStorage
    lateinit var fStorageRef: StorageReference
    lateinit var fAuth : FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_upload, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dbRef = FirebaseDatabase.getInstance().getReference("uploaded")
        fStorage = FirebaseStorage.getInstance()
        fStorageRef = fStorage.reference
        fAuth = FirebaseAuth.getInstance()
        btnSelectImg.setOnClickListener {
            when {
                (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) -> {
                    if (ContextCompat.checkSelfPermission(
                            activity!!,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(
                                arrayOf(
                                    Manifest.permission.READ_EXTERNAL_STORAGE
                                ),
                                PERMISSION_REQUEST_CODE
                            )
                        }
                    } else {
                        imageChooser()
                    }
                }
                else -> {
                    imageChooser()
                }
            }
        }

        do_upload.setOnClickListener {
            uploadFile()
        }
    }

    private fun imageChooser() {
        val intent = Intent().apply {
            type = "image/*"
            action = Intent.ACTION_GET_CONTENT
        }
        startActivityForResult(Intent.createChooser(intent, "Select Image"), REQUEST_IMAGE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if (grantResults.isEmpty() || grantResults[0] == PackageManager.PERMISSION_DENIED)
                    Toast.makeText(activity!!, "Izin ditolak", Toast.LENGTH_SHORT).show()
                else
                    imageChooser()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            return
        }
        when(requestCode) {
            REQUEST_IMAGE -> {
                filePathImage = data?.data!!
                try {
                    val bitmap = MediaStore.Images.Media.getBitmap(
                        activity!!.contentResolver, filePathImage)
                    Glide.with(this)
                        .load(bitmap)
                        .centerCrop()
                        .into(btnSelectImg)
                } catch (x : IOException) {
                    x.printStackTrace()
                }
            }
        }
    }

    fun GetFileExtension(uri: Uri) : String?{
        val contentResolver = activity!!.contentResolver
        val mimeTypeMap = MimeTypeMap.getSingleton()

        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri))
    }

    fun uploadFile(){
        val nameX = UUID.randomUUID().toString()
        val ref = fStorageRef.child("images/${nameX}.${GetFileExtension(filePathImage)}")
        ref.putFile(filePathImage)
            .addOnSuccessListener {
                Toast.makeText(activity!!, "Berhasil upload", Toast.LENGTH_SHORT).show()
//                progress.visibility = View.GONE
                ref.downloadUrl.addOnSuccessListener {
                    val up = ModelUpload(
                        fAuth.currentUser!!.displayName,
                        fAuth.currentUser!!.photoUrl.toString(),
                        et_ket.text.toString(),
                        it.toString()
                    )
                    dbRef.push().setValue(up)
                }
            }
            .addOnFailureListener {
                it.printStackTrace()
//                progress.visibility = View.GONE
            }
            .addOnProgressListener {
                value = (100.0*it.bytesTransferred/it.totalByteCount)
                Log.e("loading", value.toString())
//                progress.visibility = View.VISIBLE
//                progress.progress = value.toInt()
            }
    }
}
