package com.api.apirectrofitmvvmroomdb.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Favorite" )
data class FavouriteEntity(
    @PrimaryKey(autoGenerate = true)
    val favId: Int,
    val id:Int,
    val comments: Int,
    val likes: Int,
    val largeImageURL: String,
    val views: Int)
