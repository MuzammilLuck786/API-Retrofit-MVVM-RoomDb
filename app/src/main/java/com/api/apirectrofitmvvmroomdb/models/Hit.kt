package com.api.apirectrofitmvvmroomdb.models

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "Images" )
data class Hit(
    @PrimaryKey(autoGenerate = true)
    val imageId:Int,
    val comments: Int,
    val largeImageURL: String,
    val likes: Int,
    val views: Int,
    val id:Int,
    val tags:String,
    val type:String,
    val downloads:Int

):java.io.Serializable