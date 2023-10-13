package com.api.apirectrofitmvvmroomdb.interfaces

import FavImagesAdapter
import com.api.apirectrofitmvvmroomdb.models.FavouriteEntity
import com.api.apirectrofitmvvmroomdb.models.Hit


interface AdapterClick {
    fun onFavImageClick(hit: Hit)
}