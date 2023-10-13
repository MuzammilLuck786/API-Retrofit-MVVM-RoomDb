package com.api.apirectrofitmvvmroomdb.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.api.apirectrofitmvvmroomdb.NetworkUtils
import com.api.apirectrofitmvvmroomdb.api.ImageService
import com.api.apirectrofitmvvmroomdb.models.FavouriteEntity
import com.api.apirectrofitmvvmroomdb.models.Hit
import com.api.apirectrofitmvvmroomdb.models.ImagesLists
import com.api.apirectrofitmvvmroomdb.roomdatabase.ImageDatabase


class Repository(
    private val imageService: ImageService,
    private var imageDatabase: ImageDatabase,
    private val applicationContext: Context
) {

    private val imagesLiveData = MutableLiveData<ImagesLists>()

    val images: LiveData<ImagesLists>
        get() = imagesLiveData

    suspend fun getImages(page: Int) {
        imageDatabase=ImageDatabase.getInstance(applicationContext)
        if (NetworkUtils.isNetworkAvailable(applicationContext)) {
            val result = imageService.getImages(page)
            if (result.isSuccessful) {
                result.body()?.let {
                    imageDatabase.imageDao().deleteAllImages()
                    imageDatabase.imageDao().addImages(it.hits)
                    imagesLiveData.postValue(result.body())
                }

            }
        } else {
            val images = imageDatabase.imageDao().getImageList()
            val imgList = ImagesLists(images, 1, 1)
            imagesLiveData.postValue(imgList)
        }
    }

    suspend fun insertFav(fav:FavouriteEntity) {
        imageDatabase.imageDao().addToFavorite(fav)
    }

    fun getFav():LiveData<List<FavouriteEntity>>{
        return imageDatabase.imageDao().getFavImgList()
    }

    suspend fun deleteFav(favId:Int){
        imageDatabase.imageDao().deleteFav(favId)
    }

    suspend fun getFavoriteByHitId(id:Int): FavouriteEntity? {
        return imageDatabase.imageDao().getFavById(id)
    }




    }

