package com.example.buckeyemarket.ui.item

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.buckeyemarket.R
import com.example.buckeyemarket.data.model.ItemData
import com.example.buckeyemarket.databinding.ActivityItemDetailBinding
import com.example.buckeyemarket.ui.chatting.ChatActivity
import com.google.firebase.auth.FirebaseAuth

class ItemDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityItemDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)

        var data: String?
        data = intent.getStringExtra("docId")
        val imgRef = MyApplication.storage.reference.child("images/${data}.jpg")
        imgRef.downloadUrl.addOnCompleteListener{
            task -> if (task.isSuccessful) {
                Glide.with(this)
                    .load(task.result)
                    .into(binding.itemDetailImage)
            }
        }

        val item = MyApplication.db.collection("allItems").document(data.toString())
        item.get().addOnSuccessListener { documentSnapShot ->
            val item = documentSnapShot.toObject(ItemData::class.java)
            binding.run {
                itemDetailContent.text = item?.content
                itemCategory.text = "item Category: "+item?.category
            }
        }

        findViewById<View>(R.id.chatbutton).setOnClickListener {
            val chatIntent = Intent(this, ChatActivity::class.java)
            var receiver: String?
            var name: String?

            receiver = intent.getStringExtra("receiverUID")
            name = intent.getStringExtra("name")

            chatIntent.putExtra("name", name)
            chatIntent.putExtra("receiverUID", receiver)

            startActivity(chatIntent)
        }
    }


}