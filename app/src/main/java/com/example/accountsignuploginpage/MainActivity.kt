package com.example.accountsignuploginpage

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.accountsignuploginpage.R.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {

    lateinit var mAuth: FirebaseAuth
    private lateinit var logoutButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)

        logoutButton = findViewById(R.id.logout_button)

        logoutButton.setOnClickListener {
            mAuth.signOut()
            Toast.makeText(
                this@MainActivity,
                "Logged out successfully!",
                Toast.LENGTH_LONG
            ).show()
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
        }

        mAuth = FirebaseAuth.getInstance()
    }

    override fun onStart() {
        super.onStart()
        val user: FirebaseUser? = mAuth.currentUser
        if (user == null){
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
        }
    }

}