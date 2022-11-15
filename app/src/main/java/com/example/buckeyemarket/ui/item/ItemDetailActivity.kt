package com.example.buckeyemarket.ui.item

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.buckeyemarket.R
import com.example.buckeyemarket.data.model.ItemData
import com.example.buckeyemarket.databinding.ActivityItemDetailBinding

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
            }
        }
//        val content = item.to
//        var textView: TextView = TextView(activity as Context,)
//        textView.setText(data)


    }
}