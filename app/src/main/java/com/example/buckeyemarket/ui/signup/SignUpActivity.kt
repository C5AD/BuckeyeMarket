package com.example.buckeyemarket.ui.signup

import android.content.Intent
import android.os.Bundle
import android.os.Process
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.example.buckeyemarket.R
import com.example.buckeyemarket.ui.item.MyApplication
import com.google.firebase.auth.FirebaseAuth
import com.example.buckeyemarket.ui.login.LoginActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

class SignUpActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    companion object {
        private const val TAG = "SignUpActivity"
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance()
        findViewById<View>(R.id.RegisterUser).setOnClickListener(onClickListener)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        moveTaskToBack(true)
        Process.killProcess(Process.myPid())
        finish()
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        var currentUser = this.mAuth.getCurrentUser();
    }

    var onClickListener =
        View.OnClickListener { v ->
            when (v.id) {
                R.id.RegisterUser -> signUp()
            }
        }

    private fun signUp() {
        var email = (findViewById<View>(R.id.regEmail) as EditText).text.toString()
        val password = (findViewById<View>(R.id.regPassword) as EditText).text.toString()
        val confirmPw = (findViewById<View>(R.id.ConfirmPW) as EditText).text.toString()
        if (email.isEmpty() || password.isEmpty() || confirmPw.isEmpty()) {
            startToast("Email or Password is empty.")
        } else {
            if (email.endsWith("osu.edu")) {
                if (password.length < 8) {
                    startToast("Password must be longer than 7")
                } else {
                    if (password == confirmPw) {
                        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{task : Task<AuthResult> ->
                            if (task.isSuccessful) {

                                // add data with user uid.
                                var datamap = hashMapOf(
                                    "email" to email,
                                    "phone" to "",
                                    "name" to "",
                                    "venmoID" to ""
                                 )
                                MyApplication.db.collection("users").document(task.result.user?.uid.toString()).set(datamap)

                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success")
                                // E-mail Confirmation
                                mAuth.currentUser?.sendEmailVerification()
                                    ?.addOnCompleteListener(OnCompleteListener<Void?> { task ->
                                        if (task.isSuccessful) {
                                            afterSignUpAlertMsg()
                                        } else {
                                            startToast("Registration failed")
                                        }
                                    })
                            // UI
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.exception)
                            // UI
                            }
                        }
                    } else {
                        startToast("Password does not match")
                    }
                }
            } else {
                startToast("E-mail domain must be osu.edu")
            }
        }
    }

    private fun startToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun startLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun afterSignUpAlertMsg() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Please check your OSU email")
            .setCancelable(false)
            .setPositiveButton("OK") { dialog, id -> //do things
                startLoginActivity()
            }
        val alert = builder.create()
        alert.show()
    }
}

object SignUpTest{
    fun validateRegistrationInput(email: String) : Boolean {
        if (email.endsWith("osu.edu")) {
            return true
        }
        return false
    }
    fun validateLengthPassword(password: String): Boolean {
        if (password.length < 7) {
            return false
        }
        return true
    }
}