package com.example.accountsignuploginpage

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class LoginActivity: AppCompatActivity() {

    lateinit var mAuth: FirebaseAuth
    private lateinit var etLoginEmail: TextInputEditText
    private lateinit var etLoginPass: TextInputEditText
    private lateinit var loginButton: Button
    private lateinit var registerText: TextView
    private lateinit var forgotPassText: TextView
    private lateinit var loginBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etLoginEmail = findViewById(R.id.etLoginEmail)
        etLoginPass = findViewById(R.id.etLoginPassword)
        loginButton = findViewById(R.id.loginButton)
        registerText = findViewById(R.id.register_text)
        forgotPassText = findViewById(R.id.forgot_pass)
        loginBar = findViewById(R.id.login_bar)

        mAuth = FirebaseAuth.getInstance()

        loginButton.setOnClickListener {
            loginUser()
        }
        registerText.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }
        forgotPassText.setOnClickListener {
            showPassResetDialog()
        }
    }

    private fun showPassResetDialog(){
        val email = EditText(this@LoginActivity)
        MaterialAlertDialogBuilder(this@LoginActivity)
            .setTitle("Reset password?")
            .setMessage("Enter your email id to receive the reset link in your mail.")
            .setView(email)
            .setPositiveButton("Reset"){ _, _->
                val mailId: String = email.text.toString()
                mAuth.sendPasswordResetEmail(mailId).addOnCompleteListener {
                    if (it.isSuccessful){
                        Toast.makeText(
                            this@LoginActivity,
                            "Reset link sent to your mail",
                            Toast.LENGTH_LONG
                        ).show()
                    } else{
                        Toast.makeText(
                            this@LoginActivity,
                            "${it.exception?.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
            .setNegativeButton("Cancel"){_, _ -> }
            .show()
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
            loginBar.visibility = View.VISIBLE
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful){
                    loginBar.visibility = View.GONE
                    Toast.makeText(
                        this@LoginActivity,
                        "Logged in successfully!",
                        Toast.LENGTH_LONG
                    ).show()
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                } else {
                    loginBar.visibility = View.GONE
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