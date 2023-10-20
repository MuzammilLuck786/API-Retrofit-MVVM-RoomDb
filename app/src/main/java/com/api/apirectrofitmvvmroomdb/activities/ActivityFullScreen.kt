package com.api.apirectrofitmvvmroomdb.activities

import android.content.res.Resources
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import com.api.apirectrofitmvvmroomdb.databinding.ActivityFullScreenBinding
import com.api.apirectrofitmvvmroomdb.models.Hit
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.api.apirectrofitmvvmroomdb.R
import com.api.apirectrofitmvvmroomdb.others.ImageApplication
import com.api.apirectrofitmvvmroomdb.viewmodels.MainViewModel
import com.api.apirectrofitmvvmroomdb.viewmodels.MainViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ActivityFullScreen : AppCompatActivity() {
    private lateinit var binding: com.api.apirectrofitmvvmroomdb.databinding.ActivityFullScreenBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFullScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = (application as ImageApplication).repository

        mainViewModel = ViewModelProvider(this, MainViewModelFactory(repository))[MainViewModel::class.java]

        val hit = intent.getSerializableExtra("Hit") as Hit

         binding.pbfullimg.visibility = View.VISIBLE

        Glide.with(this@ActivityFullScreen)
            .load(hit.largeImageURL)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.pbfullimg.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.pbfullimg.visibility = View.GONE
                    return false
                }
            })
            .into(binding.imgDetail)

        binding.likesTextViewdetail.text = "${hit.likes.toString()}"
        binding.viewsTextViewdetail.text = "${hit.views.toString()}"
        binding.downloadsTextViewdetail.text = "${hit.downloads.toString()}"
        binding.commentsTextViewdetail.text = "${hit.comments.toString()}"
        binding.typesTextViewdetail.text = "${hit.type}"
        binding.tagsTextViewdetail.text = "${hit.tags}"

        binding.detailHeart.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
               val exist= mainViewModel.getFavoriteByHitId(hit.id)
                if (exist==null){
                    mainViewModel.insertFav(hit)
                    binding.detailHeart.setImageDrawable(ContextCompat.getDrawable(this@ActivityFullScreen, R.drawable.favorite))

                }
                else{
                    mainViewModel.deleteFav(hit.id)
                    binding.detailHeart.setImageDrawable(ContextCompat.getDrawable(this@ActivityFullScreen, R.drawable.redunfavorite))
                }
            }


        }
    }
}
