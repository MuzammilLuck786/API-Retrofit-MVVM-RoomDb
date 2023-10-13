package com.api.apirectrofitmvvmroomdb.viewmodels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.api.apirectrofitmvvmroomdb.models.FavouriteEntity
import com.api.apirectrofitmvvmroomdb.models.Hit
import com.api.apirectrofitmvvmroomdb.models.ImagesLists
import com.api.apirectrofitmvvmroomdb.repository.Repository
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository): ViewModel() {
    init {
        GlobalScope.launch(Dispatchers.IO) {
            repository.getImages(1)
        }
    }

    val images: LiveData<ImagesLists>
        get() = repository.images

    fun insertFav(hit: Hit){
        viewModelScope.launch(Dispatchers.IO) {
            val favouriteEntity=FavouriteEntity(0,hit.id,hit.comments,hit.likes,hit.largeImageURL,hit.views)
            val present=repository.getFavoriteByHitId(hit.id)
            if (present==null){
              repository.insertFav(favouriteEntity)
            }
            else{
                repository.deleteFav(favouriteEntity.id)
            }
        }
        }

        suspend fun getFavoriteByHitId(hitId:Int){
            repository.getFavoriteByHitId(hitId)
        }

        suspend fun deleteFav(id:Int){
            repository.deleteFav(id)
        }
       fun getFavList():LiveData<List<FavouriteEntity>>{
        return repository.getFav()
      }
}


