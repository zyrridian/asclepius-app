package com.dicoding.asclepius.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.dicoding.asclepius.data.local.entity.CancerEntity
import com.dicoding.asclepius.data.local.entity.NewsEntity

@Dao
interface NewsDao {

    @Query("SELECT * FROM news")
    fun getNews(): LiveData<List<NewsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(news: List<NewsEntity>)

    @Query("SELECT * FROM cancer")
    fun getCancer(): LiveData<List<CancerEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCancer(cancer: CancerEntity)

}