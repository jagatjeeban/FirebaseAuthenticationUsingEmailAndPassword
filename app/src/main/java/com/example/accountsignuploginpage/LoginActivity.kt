package com.example.accountsignuploginpage

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class LoginActivity: AppCompatActivity() {

    lateinit var mAuth: FirebaseAuth
    private lateinit var etLoginEmail: TextInputEditText
    private lateinit var etLoginPass: TextInputEditText
    private lateinit var loginButton: Button
    private lateinit var registerText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etLoginEmail = findViewById(R.id.etLoginEmail)
        etLoginPass = findViewById(R.id.etLoginPassword)
        loginButton = findViewById(R.id.loginButton)
        registerText = findViewById(R.id.register_text)

        mAuth = FirebaseAuth.getInstance()

        loginButton.setOnClickListener {
            loginUser()
        }
        registerText.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }
    }

    private fun loginUser() {
        val email: String = etLoginEmail.text.toString()
        val password: String = etLoginPass.text.toString()

        if (email.isEmpty()){
            etLoginEmail.error = "Email can't be empty"
            etLoginEmail.requestFocus()
        } else if (password.isEmpty()){
            etLoginPass.error = "Password can't be empty"
            etLoginPass.requestFocus()
        } else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful){
                    Toast.makeText(
                        this@LoginActivity,
                        "Logged in successfully!",
                        Toast.LENGTH_LONG
                    ).show()
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                } else {
                    Toast.makeText(
                        this@LoginActivity,
                        "${it.exception?.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}