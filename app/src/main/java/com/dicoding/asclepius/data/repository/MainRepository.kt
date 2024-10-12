package com.dicoding.asclepius.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.dicoding.asclepius.data.local.entity.NewsEntity
import com.dicoding.asclepius.data.local.room.NewsDao
import com.dicoding.asclepius.data.remote.retrofit.ApiService
import com.dicoding.asclepius.helper.Result

class MainRepository(
    private val apiService: ApiService,
    private val newsDao: NewsDao
) {

    fun getNews(): LiveData<Result<List<NewsEntity>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getNews()
            val news = response.articles
            val newsList = news?.map {
                // todo: isFavorite if needed
                NewsEntity(
                    publishedAt = it?.publishedAt,
                    author = it?.author,
                    urlToImage = it?.urlToImage,
                    description = it?.description,
                    title = it?.title,
                    url = it?.url,
                    content = it?.content
                )
            }
            if (newsList != null) newsDao.insertNews(newsList)

        } catch (e: Exception) {
            Log.d("NewsRepository", "getNews(): ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }

        // Save data to room
        val localData: LiveData<Result<List<NewsEntity>>> =
            newsDao.getNews().map { Result.Success(it) }
        emitSource(localData)
    }

    companion object {
        @Volatile
        private var instance: MainRepository? = null
        fun getInstance(
            apiService: ApiService,
            newsDao: NewsDao
        ): MainRepository = instance ?: synchronized(this) {
            instance ?: MainRepository(
                apiService,
                newsDao
            )
        }.also { instance = it }
    }

}