package com.rizki.androidlatihanmix_fadilarizki.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.rizki.androidlatihanmix_fadilarizki.R
import com.rizki.androidlatihanmix_fadilarizki.activity.LoginActivity
import kotlinx.android.synthetic.main.fragment_login.tEmail
import kotlinx.android.synthetic.main.fragment_login.tPassword
import kotlinx.android.synthetic.main.fragment_register.*

class FragmentRegister(button : Button) : Fragment() {

    val btn = button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fAuth = FirebaseAuth.getInstance()
        btn.setOnClickListener {
            val Semail = tEmail.editText!!.text.toString()
            val Spassword = tPassword.editText!!.text.toString()
            if (Semail.isNotEmpty() && Spassword.isNotEmpty() && Spassword.length >= 6
                && Spassword.equals(tPasswordComfirm.editText!!.text.toString())) {
                fAuth.createUserWithEmailAndPassword(Semail, Spassword)
                    .addOnCompleteListener {
                        val a = activity!! as LoginActivity
                        a.updateUI(fAuth.currentUser)
                    }
            } else
                Toast.makeText(activity!!, "Isi dengan benar / password min 6 char", Toast.LENGTH_SHORT).show()
        }
    }
}
