package com.example.buckeyemarket.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.buckeyemarket.ui.item.MainActivity

import com.example.buckeyemarket.R
import com.example.buckeyemarket.databinding.ActivityLoginBinding
import com.example.buckeyemarket.ui.signup.SignUpActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var binding: ActivityLoginBinding
    lateinit var db: FirebaseFirestore

    companion object {
        private const val TAG = "LoginActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        setContentView(R.layout.activity_login)

        findViewById<Button>(R.id.btn_register).setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        findViewById<View>(R.id.login).setOnClickListener(onClickListener)
    }

    private var onClickListener =
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
                    if (task.isSuccessful && mAuth.currentUser?.isEmailVerified == true) {
                        Log.d(TAG, "signInWithEmail:success")
                        val intent= Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
                    else if (mAuth.currentUser?.isEmailVerified == false) {
                        Toast.makeText(this, "Email not verified", Toast.LENGTH_SHORT).show()
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                    }
                    else {
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
