package com.dicoding.asclepius.view.viewmodel

import androidx.lifecycle.ViewModel
import com.dicoding.asclepius.data.MainRepository
import com.dicoding.asclepius.data.local.entity.CancerEntity

class MainViewModel(private val repository: MainRepository) : ViewModel() {
    fun getNews() = repository.getNews(page = 1, pageSize = 50)
    fun getCancers() = repository.getCancers()
    suspend fun setCancer(cancer: CancerEntity) = repository.setCancerHistory(cancer)
}