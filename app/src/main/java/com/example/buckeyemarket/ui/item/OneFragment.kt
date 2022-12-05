package com.example.buckeyemarket.ui.item

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.buckeyemarket.data.model.ItemData
import com.example.buckeyemarket.databinding.FragmentOneBinding
import com.example.buckeyemarket.recycler.MyAdapter
import com.example.buckeyemarket.ui.item.MyApplication

class OneFragment : Fragment() {
    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Get data from Firebase database
        val db = MyApplication.db
        val binding = FragmentOneBinding.inflate(inflater, container, false)
        // Set item list from database
        db.collection("allItems").get().addOnSuccessListener {
            result -> val itemList = mutableListOf<ItemData>()
            for (doc in result) {
                val item = doc.toObject(ItemData::class.java)
                item.docId = doc.id
                itemList.add(item)
                val imgRef = MyApplication.storage.reference.child("images/${item.docId}")
            }
            // set the recyclerView with the itemList
            binding.recyclerView.layoutManager = LinearLayoutManager(activity)
            binding.recyclerView.adapter = MyAdapter(activity as Context, itemList)
        }
            .addOnFailureListener {
                exception -> Log.d("User", "Error getting documents: ", exception)
                Toast.makeText(activity, "Failure to get data from the server", Toast.LENGTH_SHORT).show()
            }
        return binding.root

    }
}
