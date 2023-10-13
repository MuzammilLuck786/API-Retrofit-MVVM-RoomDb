package com.api.apirectrofitmvvmroomdb.api

import com.api.apirectrofitmvvmroomdb.models.ImagesLists
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ImageService {

    @GET("?key=39775992-3a11de0473458c29c955e3930")
    suspend fun getImages(@Query("page") page: Int): Response<ImagesLists>
}
