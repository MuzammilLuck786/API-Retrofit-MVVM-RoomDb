package com.api.apirectrofitmvvmroomdb.activities


import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.provider.CalendarContract.Colors
import android.view.View
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.api.apirectrofitmvvmroomdb.R
import com.api.apirectrofitmvvmroomdb.adapter.ImageAdapter
import com.api.apirectrofitmvvmroomdb.databinding.ActivityMainBinding
import com.api.apirectrofitmvvmroomdb.interfaces.AdapterClick
import com.api.apirectrofitmvvmroomdb.models.Hit
import com.api.apirectrofitmvvmroomdb.others.ImageApplication
import com.api.apirectrofitmvvmroomdb.viewmodels.MainViewModel
import com.api.apirectrofitmvvmroomdb.viewmodels.MainViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() , AdapterClick{

    private lateinit var imageAdapter: ImageAdapter
    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val repository = (application as ImageApplication).repository
        binding.checkFav.setImageResource(R.drawable.whitefavorite)

        // Initialize MainViewModel
        mainViewModel = ViewModelProvider(this, MainViewModelFactory(repository))[MainViewModel::class.java]

        val categoryViews = arrayOf(
            binding.tvBackground,
            binding.tvFashion,
            binding.tvNature,
            binding.tvScience,
            binding.tvEducation,
            binding.tvFellings,
            binding.tvHealth,
            binding.tvComputer,
            binding.tvFood,
            binding.tvSports,
            binding.tvTravel
        )
        mainViewModel.getImage("backgrounds")
        binding.tvBackground.setTextColor(getColor(R.color.black))
        categoryViews.forEach { categoryView ->
            categoryView.setOnClickListener {
                categoryViews.forEach {
                    it.setTextColor(getColor(R.color.white))
                }
                categoryView.setTextColor(getColor(R.color.black))
                val selectedCategory = categoryView.text.toString()
                mainViewModel.getImage(selectedCategory)
            }
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        imageAdapter = ImageAdapter(this@MainActivity,emptyList(),this,null)
        binding.recyclerView.adapter = imageAdapter
        imageAdapter.notifyDataSetChanged()

        binding.pbHitList.visibility=View.VISIBLE
        mainViewModel.images.observe(this) { hits ->
            imageAdapter.submitList(hits.hits)
            binding.pbHitList.visibility=View.GONE
        }

        binding.checkFav.setOnClickListener {
            val intent=Intent(this@MainActivity, FavoriteActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onFavImageClick(hit: Hit) {
        mainViewModel.insertFav(hit)
    }
}
