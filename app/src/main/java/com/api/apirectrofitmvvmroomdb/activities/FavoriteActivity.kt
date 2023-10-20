package com.api.apirectrofitmvvmroomdb.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.api.apirectrofitmvvmroomdb.adapter.FavImagesAdapter
import com.api.apirectrofitmvvmroomdb.others.ImageApplication
import com.api.apirectrofitmvvmroomdb.databinding.ActivityFavoriteBinding
import com.api.apirectrofitmvvmroomdb.models.FavouriteEntity
import com.api.apirectrofitmvvmroomdb.viewmodels.MainViewModel
import com.api.apirectrofitmvvmroomdb.viewmodels.MainViewModelFactory
import kotlinx.coroutines.*

class FavoriteActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var favImagesAdapter: FavImagesAdapter
    private lateinit var binding:ActivityFavoriteBinding

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = (application as ImageApplication).repository

        mainViewModel = ViewModelProvider(this, MainViewModelFactory(repository))[MainViewModel::class.java]

        binding.rvFavImage.layoutManager = LinearLayoutManager(this)


        favImagesAdapter = FavImagesAdapter(this,emptyList())
        mainViewModel.getFavList().observe(this) { List ->
            favImagesAdapter.submitList(List)
            binding.rvFavImage.adapter = favImagesAdapter
            if (List.isEmpty()) {
                binding.tvEmpty.visibility = View.VISIBLE

            } else {
                binding.tvEmpty.visibility=View.INVISIBLE
                binding.rvFavImage.visibility = View.VISIBLE
            }
        }

        favImagesAdapter.setOnFavouriteClickListner(object : FavImagesAdapter.FavBtnClickListner
        {
            override fun onFavBtnClick(item: FavouriteEntity) {
                GlobalScope.launch {
                    mainViewModel.deleteFav(item.id)
                }
            }
        })

    }

}
