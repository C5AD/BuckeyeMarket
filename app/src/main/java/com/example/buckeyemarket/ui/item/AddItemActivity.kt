package com.example.buckeyemarket.ui.item

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.buckeyemarket.R
import com.example.buckeyemarket.databinding.ActivityAddItemBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.StorageReference
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class AddItemActivity : AppCompatActivity() {


    lateinit var filePath: String
    lateinit var binding: ActivityAddItemBinding
    private lateinit var mAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddItemBinding.inflate(layoutInflater)
        mAuth = FirebaseAuth.getInstance()
        setContentView(binding.root)
        setSupportActionBar(binding.addToolbar)
        val locateMe = binding.locateMeButton
        // onClickListener when the user click the + button
        if (locateMe != null) {
            locateMe.setOnClickListener{
                val intent = Intent(this, LocateMeActivity::class.java)
                startActivity(intent)
            }
        }
    }
    // Launcher to get the image from the user and show it into add_image_view
    val requestLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == android.app.Activity.RESULT_OK) {
            Glide
                .with(applicationContext)
                .load(it.data?.data)
                .apply(RequestOptions().override(250, 200))
                .centerCrop()
                .into(binding.addImageView)

            val cursor = contentResolver.query(it.data?.data as Uri, arrayOf<String>(MediaStore.Images.Media.DATA), null, null, null);
            cursor?.moveToFirst().let {
                filePath = cursor?.getString(0) as String
            }
        }
    }
    // function to set the image and image path
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId === R.id.menu_add_item) {
            val intent = Intent(Intent.ACTION_PICK)
            intent.setDataAndType(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*"
            )
            requestLauncher.launch(intent)
        } else if (item.itemId === R.id.menu_add_save) {
            if(binding.addImageView.drawable !== null && binding.addEditView.text.isNotEmpty()) {
                // save data to the store and then declare the upload file name as document id
                saveStore()
            } else {
                Toast.makeText(this, "Data is not completed", Toast.LENGTH_SHORT).show()
            }

        }
        return super.onOptionsItemSelected(item)
    }

    // Create the header menu for additemActivity
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }
    // Function to convert the date in yyyy-MM-dd format
    private fun dateToString(date: Date): String {
        val format = SimpleDateFormat("yyyy-MM-dd")
        return format.format(date)
    }
    // Function to save the contents that the user want to upload into Firebase database
    private fun saveStore() {

        val data = mapOf(
            "email" to MyApplication.email,
            "content" to binding.addEditView.text.toString(),
            "date" to dateToString(Date()),
            "seller" to mAuth.currentUser
        )
        MyApplication.db.collection("allItems").add(data).addOnSuccessListener {
            uploadImage(it.id)
        }.addOnFailureListener {
            Log.w("User", "data save error", it)
        }

    }
    // Function to save the image into Firebase storage
    private fun uploadImage(docId: String) {
        val storage = MyApplication.storage

        val storageRef: StorageReference = storage.reference
        val imgRef: StorageReference = storageRef.child("images/${docId}.jpg")

        var file = Uri.fromFile(File(filePath))
        imgRef.putFile(file).addOnFailureListener {
            Log.d("User", "failure..."+it)
        }.addOnSuccessListener {
            Toast.makeText(this, "Successfully saved", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

}