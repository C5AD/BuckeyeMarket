package com.example.buckeyemarket.ui.userSetting

import android.content.Intent
import android.os.Bundle
import android.os.Process
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.buckeyemarket.R
import com.example.buckeyemarket.ui.item.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.example.buckeyemarket.ui.login.LoginActivity
import com.google.firebase.firestore.FirebaseFirestore

class UserSettingUpActivity : AppCompatActivity(){
    private lateinit var mAuth: FirebaseAuth
    lateinit var db: FirebaseFirestore

    companion object {
        private const val TAG = "UserSettingUpActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usersetting)

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        findViewById<View>(R.id.chatButton).setOnClickListener(editClickListener)
        findViewById<View>(R.id.removeAcc).setOnClickListener(removeClickListener)
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        var currentUser = this.mAuth.currentUser;
    }

    var editClickListener =
        View.OnClickListener { v ->
            when (v.id) {
                R.id.chatButton -> updateButton()
            }
        }

    var removeClickListener =
        View.OnClickListener { v ->
            when (v.id) {
                R.id.removeAcc -> removeAcc()
            }
        }

    override fun onBackPressed() {
        super.onBackPressed()
        moveTaskToBack(true)
        Process.killProcess(Process.myPid())
        finish()
    }

    private fun updateButton() {
        var email = (findViewById<View>(R.id.emailEdit) as EditText).text.toString()
        val name = (findViewById<View>(R.id.nameEdit) as EditText).text.toString()
        val venmo = (findViewById<View>(R.id.venmoIDEdit) as EditText).text.toString()
        if (email.isEmpty() && name.isEmpty() && venmo.isEmpty()) {
            startToast("Email or Password is empty.")
        }
        else if (!email.endsWith("osu.edu") && name.isEmpty() && venmo.isEmpty()) {
            startToast("Email has to be end with osu.edu")
        } else {
            var uid = mAuth.currentUser?.uid;
            var userInfo = db.collection("users").document(uid.toString())

            if (email.isNotEmpty() && email.endsWith("osu.edu")) {
                userInfo.update("email", email)
                mAuth.currentUser?.updateEmail(email);
            }

            if (name.isNotEmpty()) {
                userInfo.update("name", name)
            }
            if (venmo.isNotEmpty()) {
                userInfo.update("venmoID", venmo)
            }
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
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