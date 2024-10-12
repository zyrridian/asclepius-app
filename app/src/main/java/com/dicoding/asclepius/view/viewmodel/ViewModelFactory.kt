package com.dicoding.asclepius.view.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.asclepius.data.repository.MainRepository
import com.dicoding.asclepius.di.Injection

class ViewModelFactory(
    private val repository: MainRepository
    // todo: settings preference if needed
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown viewmodel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                val repository = Injection.provideRepository(context)
                instance ?: ViewModelFactory(repository)
            }.also { instance = it }
    }

}