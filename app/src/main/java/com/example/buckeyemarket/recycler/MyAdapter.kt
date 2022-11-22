package com.example.buckeyemarket.recycler

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.buckeyemarket.data.model.ItemData
import com.example.buckeyemarket.databinding.ItemMainBinding
import com.example.buckeyemarket.databinding.ItemRecyclerviewBinding
import com.example.buckeyemarket.ui.item.AddItemActivity
import com.example.buckeyemarket.ui.item.ItemDetailActivity
import com.example.buckeyemarket.ui.item.MyApplication

class MyViewHolder(val binding: ItemMainBinding) : RecyclerView.ViewHolder(binding.root)
class MyAdapter (val context: Context, val itemList: MutableList<ItemData>) : RecyclerView.Adapter<MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MyViewHolder(ItemMainBinding.inflate(layoutInflater))
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = itemList.get(position)
        // set the all item_main.xml's views' text corresponding to the data
        holder.binding.run {
            itemEmailView.text = data.email
            itemDateView.text = data.date
            itemContentView.text = data.content
        }
        // get the image reference from the firebase storage and set it to the item_image_view
        val imgRef = MyApplication.storage
            .reference.child("images/${data.docId}.jpg")

        imgRef.downloadUrl.addOnCompleteListener {
            task -> if (task.isSuccessful) {
                Glide.with(context)
                    .load(task.result)
                    .into(holder.binding.itemImageView)
            }
        }
        holder.binding.itemImageView.setOnClickListener{
            val mActivity = context as Activity
            val intent = Intent(mActivity, ItemDetailActivity::class.java)
            intent.putExtra("docId", data.docId.toString())
            intent.putExtra("receiverUID", data.seller.toString())
            intent.putExtra("name", data.email.toString())
            mActivity.startActivity(intent)
        }

    }
    override fun getItemCount(): Int {
        return itemList.size
    }

}