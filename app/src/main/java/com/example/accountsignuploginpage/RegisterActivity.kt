package com.example.accountsignuploginpage

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    lateinit var mAuth: FirebaseAuth
    private lateinit var regButton: Button
    private lateinit var loginText: TextView
    private lateinit var etRegEmail: TextInputEditText
    private lateinit var etRegPassword: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        regButton = findViewById(R.id.regButton)
        loginText = findViewById(R.id.login_text)
        etRegEmail = findViewById(R.id.etRegEmail)
        etRegPassword = findViewById(R.id.etRegPassword)

        mAuth = FirebaseAuth.getInstance()

        regButton.setOnClickListener {
            createUser()
        }
        loginText.setOnClickListener {
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
        }

    }

    private fun createUser(){
        val email: String = etRegEmail.text.toString()
        val password: String = etRegPassword.text.toString()

        if (email.isEmpty()){
            etRegEmail.error = "Email can't be empty"
            etRegEmail.requestFocus()
        } else if (password.isEmpty()){
            etRegPassword.error = "Password can't be empty"
            etRegPassword.requestFocus()
        } else {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful){
                    Toast.makeText(
                        this@RegisterActivity,
                        "Registered successfully!",
                        Toast.LENGTH_LONG
                    ).show()
                    startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                } else {
                    Toast.makeText(
                        this@RegisterActivity,
                        "${it.exception?.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}