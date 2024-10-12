package com.dicoding.asclepius.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dicoding.asclepius.data.local.entity.NewsEntity

@Database(entities = [NewsEntity::class], version = 1, exportSchema = false)
abstract class MainDatabase : RoomDatabase() {

    abstract fun newsDao(): NewsDao

    companion object {
        @Volatile
        private var instance: MainDatabase? = null
        fun getInstance(context: Context): MainDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    MainDatabase::class.java, "Main.db"
                ).fallbackToDestructiveMigration().build()
            }
    }

}