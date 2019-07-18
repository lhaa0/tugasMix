package com.rizki.androidlatihanmix_fadilarizki

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.rizki.androidlatihanmix_fadilarizki.fragment.FragmentMain
import com.rizki.androidlatihanmix_fadilarizki.fragment.FragmentUpload
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fAuth = FirebaseAuth.getInstance()
//        logout.setOnClickListener {
//            fAuth.signOut()
//            startActivity(Intent(this, LoginActivity::class.java))
//            finish()
//        }
        getSupportFragmentManager().beginTransaction()
            .replace(R.id.fragment, FragmentMain())
            .commit();
        bottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.main -> {
                    getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment, FragmentMain())
                        .commit();
                }
                R.id.upload -> {
                    getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment, FragmentUpload())
                        .commit();
                }
            }
            true
        }

    }
}
