package com.example.buckeyemarket

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.graphics.drawable.DrawableContainer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.buckeyemarket.databinding.FragmentOneBinding
import com.example.buckeyemarket.databinding.ItemRecyclerviewBinding

// Role for category view
class MyViewHolder(val binding: ItemRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root)

// Adapter
class MyAdapter(val datas:MutableList<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemCount(): Int {
        return datas.size
    }

    // Auto call to prepare view holder which has category view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = MyViewHolder(
        ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    // Call to construct the each category
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as MyViewHolder).binding
        // print out the data to View
        binding.itemData.text = datas[position]
    }

}

// decoration for RecyclerView
class MyDecoration(val context: Context): RecyclerView.ItemDecoration() {
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)

        // calculate view size
        val width = parent.width
        val height = parent.height

        // calculate image size
        val dr: Drawable? = ResourcesCompat.getDrawable(context.getResources(), androidx.appcompat.R.drawable.abc_list_longpressed_holo, null)
        val drWidth = dr?.intrinsicWidth
        val drHeight = dr?.intrinsicHeight

        // calculate location to show the image
        val left = width / 2 - drWidth?.div(2) as Int
        val top = height / 2 - drHeight?.div(2) as Int

        // print out the image
        c.drawBitmap(
            BitmapFactory.decodeResource(context.getResources(), androidx.appcompat.R.drawable.abc_list_longpressed_holo), left.toFloat(), top.toFloat(), null
        )
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val idx = parent.getChildAdapterPosition(view) + 1

        if (idx % 3 == 0)
            outRect.set(10, 10, 10, 50)
        else
            outRect.set(10, 10, 10, 0)
        view.setBackgroundColor(Color.parseColor("#28A0FF"))
        ViewCompat.setElevation(view, 20.0f)
    }
}

class OneFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentOneBinding.inflate(inflater, container, false)
        // Dummy
        val datas = mutableListOf<String>()
        for (i in 0..9) {
            datas.add("Item $i")
        }
        val layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.layoutManager = layoutManager
        val adapter = MyAdapter(datas)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(MyDecoration(activity as Context))
        return binding.root
    }
}
