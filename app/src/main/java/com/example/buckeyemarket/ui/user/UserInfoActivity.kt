package com.example.buckeyemarket.ui.user

import android.content.Intent
import android.os.Bundle
import android.os.Process
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.buckeyemarket.R
import com.example.buckeyemarket.ui.chatting.ChatActivity
import com.example.buckeyemarket.ui.item.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.example.buckeyemarket.ui.login.LoginActivity
import com.google.firebase.firestore.FirebaseFirestore

class UserInfoActivity : AppCompatActivity(){
    private lateinit var mAuth: FirebaseAuth
    lateinit var db: FirebaseFirestore

    companion object {
        private const val TAG = "UserSettingUpActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_userinfo)

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        var currentUser = this.mAuth.currentUser;
    }

    override fun onBackPressed() {
        super.onBackPressed()
        moveTaskToBack(true)
        Process.killProcess(Process.myPid())
        finish()
    }


    private fun startToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

}