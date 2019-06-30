package com.rizki.androidlatihanmix_fadilarizki.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.rizki.androidlatihanmix_fadilarizki.MainActivity
import com.rizki.androidlatihanmix_fadilarizki.R
import com.rizki.androidlatihanmix_fadilarizki.activity.LoginActivity
import kotlinx.android.synthetic.main.fragment_login.*

class FragmentLogin(button : Button) : Fragment() {

    private lateinit var fAuth: FirebaseAuth
    val btn = button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fAuth = FirebaseAuth.getInstance()

        btn.setOnClickListener {
            val Semail = tEmail.editText!!.text.toString()
            val Spassword = tPassword.editText!!.text.toString()
            if (Semail.isNotEmpty() && Spassword.isNotEmpty()) {
                fAuth.signInWithEmailAndPassword(Semail, Spassword)
                    .addOnCompleteListener {
                        val a = activity!! as LoginActivity
                        a.updateUI(fAuth.currentUser)
                    }
            } else
                Toast.makeText(activity!!, "email atau password tidak boleh kosong", Toast.LENGTH_SHORT).show()
        }

    }
}
