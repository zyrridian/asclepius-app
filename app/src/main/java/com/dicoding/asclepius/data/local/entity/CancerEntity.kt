package com.dicoding.asclepius.data.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "cancer")
class CancerEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val label: String? = null,
    val confidence: Float? = null,
    val image: ByteArray? = null,
) : Parcelable

