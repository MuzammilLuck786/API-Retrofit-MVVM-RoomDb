package com.api.apirectrofitmvvmroomdb

import android.app.Application
import com.api.apirectrofitmvvmroomdb.api.ImageService
import com.api.apirectrofitmvvmroomdb.api.RetrofitHelper
import com.api.apirectrofitmvvmroomdb.repository.Repository
import com.api.apirectrofitmvvmroomdb.roomdatabase.ImageDatabase

class ImageApplication: Application() {
    lateinit var repository: Repository

    override fun onCreate() {
        super.onCreate()
        initialize()
    }

    private fun initialize() {
        val imageService = RetrofitHelper.getInstance().create(ImageService::class.java)
        val imageDatabase=ImageDatabase.getInstance(applicationContext)
        repository = Repository(imageService,imageDatabase,applicationContext)
    }
}
