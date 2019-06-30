package com.rizki.androidlatihanmix_fadilarizki

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.rizki.androidlatihanmix_fadilarizki.activity.LoginActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fAuth = FirebaseAuth.getInstance()
        logout.setOnClickListener {
            fAuth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
