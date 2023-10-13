package com.api.apirectrofitmvvmroomdb

import FavImagesAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.api.apirectrofitmvvmroomdb.adapter.ImageAdapter
import com.api.apirectrofitmvvmroomdb.interfaces.AdapterClick
import com.api.apirectrofitmvvmroomdb.models.FavouriteEntity
import com.api.apirectrofitmvvmroomdb.models.Hit
import com.api.apirectrofitmvvmroomdb.viewmodels.MainViewModel
import com.api.apirectrofitmvvmroomdb.viewmodels.MainViewModelFactory
import kotlinx.coroutines.*

class FavoriteActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var mainViewModel: MainViewModel
    private lateinit var favImagesAdapter: FavImagesAdapter

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        val repository = (application as ImageApplication).repository

        mainViewModel = ViewModelProvider(this, MainViewModelFactory(repository))[MainViewModel::class.java]

        recyclerView = findViewById(R.id.rvFavImage)
        recyclerView.layoutManager = LinearLayoutManager(this)

        favImagesAdapter = FavImagesAdapter(this,emptyList())
        mainViewModel.getFavList().observe(this) { List ->
            recyclerView.adapter = favImagesAdapter
            favImagesAdapter.submitList(List)
        }

        favImagesAdapter.setOnFavouriteClickListner(object : FavImagesAdapter.FavBtnClickListner
        {
            override fun onFavBtnClick(item: FavouriteEntity) {
                GlobalScope.launch {
                    mainViewModel.deleteFav(item.favId)
                }

            }
        })

    }

}
