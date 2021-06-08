package com.thecoolture.batikdetectionapp.data.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class History (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "scan_datetime")
    var scanDateTime: String? = null,

    @ColumnInfo(name = "batik_name")
    var batikName: String? = null
) : Parcelable