package com.dicoding.asclepius.view.viewmodel

import androidx.lifecycle.ViewModel
import com.dicoding.asclepius.data.repository.MainRepository

class MainViewModel(
    private val repository: MainRepository,
    // todo: settings preference
) : ViewModel() {

    fun getNews() = repository.getNews()

}