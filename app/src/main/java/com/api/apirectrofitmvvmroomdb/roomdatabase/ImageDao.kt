package com.api.apirectrofitmvvmroomdb.roomdatabase

import android.text.BoringLayout
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.api.apirectrofitmvvmroomdb.models.FavouriteEntity
import com.api.apirectrofitmvvmroomdb.models.Hit

@Dao
interface ImageDao {

    // Operations related to Hits (Images)

    @Insert
    suspend fun addImages(images: List<Hit>)

    @Query("SELECT * FROM Images")
    suspend fun getImageList(): List<Hit>

    @Query("DELETE FROM Images")
    suspend fun deleteAllImages()

    @Query("DELETE FROM Images WHERE id=:id")
    suspend fun delFromImages(id: Int)

    // Operations related to Favorites

    @Insert
    suspend fun addToFavorite(favouriteEntity: FavouriteEntity)

    @Query("SELECT * FROM Favorite")
    fun getFavImgList(): LiveData<List<FavouriteEntity>>

    @Query("DELETE FROM Favorite WHERE id=:id")
    suspend fun deleteFav(id: Int)

    @Query("SELECT * FROM Favorite WHERE id=:id")
    suspend fun getFavById(id: Int): FavouriteEntity?

//    @Query("SELECT EXISTS (SELECT 1 FROM Favorite WHERE id = :id)")
//    suspend fun isFavorite(id:Int):Boolean


}
