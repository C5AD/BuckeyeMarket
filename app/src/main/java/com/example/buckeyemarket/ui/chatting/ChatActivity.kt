package com.example.buckeyemarket.ui.chatting

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

class ChatActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    lateinit var db: FirebaseFirestore

    companion object {
        private const val TAG = "ChatActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        setListners()
    }

    private fun loadReceiverInfo() {
       db.

    }

    private fun setListners() {

    }

}