package com.dicoding.asclepius.di

import android.content.Context
import com.dicoding.asclepius.data.local.room.MainDatabase
import com.dicoding.asclepius.data.remote.retrofit.ApiConfig
import com.dicoding.asclepius.data.MainRepository

object Injection {
    fun provideRepository(context: Context): MainRepository {
        val apiService = ApiConfig.getApiService()
        val database = MainDatabase.getInstance(context)
        val dao = database.newsDao()
        return MainRepository.getInstance(apiService, dao)
    }
}