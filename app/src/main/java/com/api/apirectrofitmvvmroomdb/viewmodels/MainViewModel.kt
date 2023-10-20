package com.api.apirectrofitmvvmroomdb.viewmodels

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.api.apirectrofitmvvmroomdb.R
import com.api.apirectrofitmvvmroomdb.databinding.ActivityMainBinding
import com.api.apirectrofitmvvmroomdb.models.FavouriteEntity
import com.api.apirectrofitmvvmroomdb.models.Hit
import com.api.apirectrofitmvvmroomdb.models.ImagesLists
import com.api.apirectrofitmvvmroomdb.repository.Repository
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.nio.file.DirectoryStream

@OptIn(DelicateCoroutinesApi::class)
class MainViewModel(private val repository: Repository): ViewModel() {

    val images: LiveData<ImagesLists>
        get() = repository.images

    fun insertFav(hit: Hit){
        viewModelScope.launch(Dispatchers.IO) {
            val present=repository.getFavoriteByHitId(hit.id)
            val favouriteEntity=FavouriteEntity(0,hit.id,hit.comments,hit.likes,hit.largeImageURL,hit.views)
            if (present==null){
                repository.insertFav(favouriteEntity)
            }
            else{
                repository.deleteFav(favouriteEntity.id)
            }
        }
    }

        suspend fun getFavoriteByHitId(hitId:Int):FavouriteEntity?{
           return repository.getFavoriteByHitId(hitId)
        }

        suspend fun deleteFav(id:Int){
            repository.deleteFav(id)
        }
       fun getFavList():LiveData<List<FavouriteEntity>>{
        return repository.getFav()
      }

    suspend fun delFromImg(id: Int){
        repository.delFromImg(id)
    }
    fun getImage(category:String){
        GlobalScope.launch(Dispatchers.Main) {
            repository.getImages(category)
        }

    }
}


