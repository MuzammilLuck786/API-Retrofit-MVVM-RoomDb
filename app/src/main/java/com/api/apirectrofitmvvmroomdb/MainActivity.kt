package com.api.apirectrofitmvvmroomdb

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.api.apirectrofitmvvmroomdb.adapter.ImageAdapter
import com.api.apirectrofitmvvmroomdb.interfaces.AdapterClick
import com.api.apirectrofitmvvmroomdb.models.Hit
import com.api.apirectrofitmvvmroomdb.viewmodels.MainViewModel
import com.api.apirectrofitmvvmroomdb.viewmodels.MainViewModelFactory

class MainActivity : AppCompatActivity(),AdapterClick {

    private lateinit var recyclerView: RecyclerView
    private lateinit var imageAdapter: ImageAdapter
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository = (application as ImageApplication).repository

        // Initialize MainViewModel
        mainViewModel = ViewModelProvider(this, MainViewModelFactory(repository))[MainViewModel::class.java]

        // Initialize the RecyclerView
        recyclerView = findViewById(R.id.recyclerView)
        val favorites:ImageView=findViewById(R.id.checkFav)
        recyclerView.layoutManager = LinearLayoutManager(this)

        favorites.setOnClickListener {
            val intent=Intent(this@MainActivity,FavoriteActivity::class.java)
            startActivity(intent)
        }

        // Initialize the ImageAdapter
        imageAdapter = ImageAdapter(emptyList(),this) // Start with an empty list

        // Set the adapter to the RecyclerView
        recyclerView.adapter = imageAdapter

        // Observe the LiveData for updates and update the adapter
        mainViewModel.images.observe(this) { hits ->
            imageAdapter.submitList(hits.hits)
        }


    }

    override fun onFavImageClick(hit: Hit) {
        mainViewModel.insertFav(this@MainActivity,hit)
    }

}
