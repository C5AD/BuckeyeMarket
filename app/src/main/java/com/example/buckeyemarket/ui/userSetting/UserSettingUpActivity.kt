package com.example.buckeyemarket.ui.userSetting

import android.content.Intent
import android.os.Bundle
import android.os.Process
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.example.buckeyemarket.R
import com.example.buckeyemarket.databinding.ActivityLoginBinding
import com.example.buckeyemarket.ui.item.MainActivity
import com.example.buckeyemarket.ui.item.MyApplication
import com.google.firebase.auth.FirebaseAuth
import com.example.buckeyemarket.ui.login.LoginActivity
import com.example.buckeyemarket.ui.signup.SignUpActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.firestore.FirebaseFirestore

class UserSettingUpActivity : AppCompatActivity(){
    private lateinit var mAuth: FirebaseAuth
    private lateinit var binding: ActivityLoginBinding
    lateinit var db: FirebaseFirestore

    companion object {
        private const val TAG = "UserSettingUpActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usersetting)

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance()
        findViewById<View>(R.id.editButton).setOnClickListener(editClickListener)
        findViewById<View>(R.id.removeAcc).setOnClickListener(removeClickListener)
    }

    var editClickListener =
        View.OnClickListener { v ->
            when (v.id) {
                R.id.RegisterUser -> update()
            }
        }

    var removeClickListener =
        View.OnClickListener { v ->
            when (v.id) {
                R.id.RegisterUser -> removeAcc()
            }
        }

    override fun onBackPressed() {
        super.onBackPressed()
        moveTaskToBack(true)
        Process.killProcess(Process.myPid())
        finish()
    }

    private fun update() {
        var email = (findViewById<View>(R.id.emailEdit) as EditText).text.toString()
        val name = (findViewById<View>(R.id.nameEdit) as EditText).text.toString()
        val venmo = (findViewById<View>(R.id.venmoIDEdit) as EditText).text.toString()
        if (email.isEmpty() && name.isEmpty() && venmo.isEmpty()) {
            startToast("Email or Password is empty.")
        }
        else if (!email.endsWith("osu.edu")) {
            startToast("Email has to be end with osu.edu")
        } else {
            var uid = mAuth.currentUser?.uid;
            var userInfo = db.collection("users").document(uid.toString())
            if (email.isNotEmpty()) {
                userInfo.update("email", email)
                mAuth.currentUser?.updateEmail(email);
            }
            if (name.isNotEmpty()) {
                userInfo.update("name", name)
            }
            if (venmo.isNotEmpty()) {
                userInfo.update("venmoID", venmo)
            }
        }

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun removeAcc() {
        mAuth.currentUser?.delete()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }


    private fun startToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

}