package com.rizki.androidlatihanmix_fadilarizki.fragment

import android.os.Bundle
import android.util.Log.e
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.rizki.androidlatihanmix_fadilarizki.R
import com.rizki.androidlatihanmix_fadilarizki.adapter.ContentAdapter
import com.rizki.androidlatihanmix_fadilarizki.model.ModelUpload
import kotlinx.android.synthetic.main.fragment_content.*

class FragmentContent : Fragment() {

    lateinit var dbRef : DatabaseReference

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_content, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rcView.layoutManager = LinearLayoutManager(activity!!)
        dbRef = FirebaseDatabase.getInstance().getReference("uploaded")
        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                val uploads = ArrayList<ModelUpload>()
                for (data in p0.children) {
                    val upload = data.getValue(ModelUpload::class.java)
                    uploads.add(upload!!)
                }
                rcView.adapter = ContentAdapter(uploads, activity!!)
            }

            override fun onCancelled(p0: DatabaseError) {
                e("canceled", "yoi")
            }

        })
    }

    override fun onPause() {
        super.onPause()

    }
}