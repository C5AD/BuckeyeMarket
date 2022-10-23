package com.example.buckeyemarket.ui.login

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.buckeyemarket.MainActivity
import com.example.buckeyemarket.databinding.ActivityLoginBinding

import com.example.buckeyemarket.R
import com.example.buckeyemarket.SignUpActivity
import com.example.buckeyemarket.ThreeFragment
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    companion object {
        private const val TAG = "LoginActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mAuth = FirebaseAuth.getInstance()
        findViewById<View>(R.id.login).setOnClickListener(onClickListener)
    }

    var onClickListener =
        View.OnClickListener { v ->
            when (v.id) {
                R.id.login -> login()
            }
        }

    // Login function
    private fun login() {
        var email = (findViewById<View>(R.id.username) as EditText).text.toString()
        var password = (findViewById<View>(R.id.password) as EditText).text.toString()
        // Ensure that text box for email and password are not empty.
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Empty or password is empty", Toast.LENGTH_SHORT).show()
        } else {
            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this){ task: Task<AuthResult> ->
                    // If authenticate successful run the main activity
                    if (task.isSuccessful) {
                        Log.d(TAG, "signInWithEmail:success")
                        val intent= Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        var emailClear = (findViewById<View>(R.id.username) as EditText)
                        var passwordClear = (findViewById<View>(R.id.password) as EditText)
                        emailClear.setText("")
                        passwordClear.setText("")
                        Toast.makeText(this, "Incorrect email or password", Toast.LENGTH_SHORT).show()
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                    }
                }

            }
        }
    }
