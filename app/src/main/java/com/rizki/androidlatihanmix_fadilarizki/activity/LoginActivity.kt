package com.rizki.androidlatihanmix_fadilarizki.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.rizki.androidlatihanmix_fadilarizki.MainActivity
import com.rizki.androidlatihanmix_fadilarizki.R
import com.rizki.androidlatihanmix_fadilarizki.fragment.FragmentLogin
import com.rizki.androidlatihanmix_fadilarizki.fragment.FragmentRegister
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private val RC_SIGN_IN = 7
    private lateinit var mGoogleSignIn: GoogleSignInClient
    private lateinit var fAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        fAuth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(
            GoogleSignInOptions.DEFAULT_SIGN_IN
        )
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignIn = GoogleSignIn.getClient(this, gso)
        googleLogin.setOnClickListener {
            signIn()
        }

        var transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragLogin, FragmentLogin(submit))
        transaction.commit()
        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> {
                        transaction = supportFragmentManager.beginTransaction()
                        transaction.replace(R.id.fragLogin, FragmentLogin(submit))
                        transaction.addToBackStack(null);
                        transaction.commit();
                        submit.text = "LOGIN"
                    }
                    1 -> {
                        transaction = supportFragmentManager.beginTransaction()
                        transaction.replace(R.id.fragLogin, FragmentRegister(submit))
                        transaction.addToBackStack(null);
                        transaction.commit();
                        submit.text = "Register"
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
    }

    private fun signIn() {
        val signInIntent = mGoogleSignIn.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        Log.d("FAUTH_LOGIN", "firebaseAuth : ${account.id}")

        val creadential = GoogleAuthProvider.getCredential(account.idToken, null)

        fAuth.signInWithCredential(creadential).addOnCompleteListener(this) {
            if (it.isSuccessful) {
                val user = fAuth.currentUser
                updateUI(user)
            } else {
                Toast.makeText(this, "Login Gagal", Toast.LENGTH_LONG).show()
            }
        }
    }


    public fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            Toast.makeText(this, "Nama ${user.displayName}", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (ex: ApiException) {
                Log.e("ApiException", ex.message)
            }

        }
    }

    override fun onStart() {
        super.onStart()
        val user = fAuth.currentUser
        if (user != null)
            updateUI(user)
    }
}
