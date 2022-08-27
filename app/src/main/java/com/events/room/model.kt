package com.events.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved")
data class Saved(
    @PrimaryKey(autoGenerate = true) val id: Long?,
    @ColumnInfo(name = "id_e") val id_e: Long?,
    @ColumnInfo(name = "name_e") val user_id: String?,
    @ColumnInfo(name = "image_e") val name: String?,
    @ColumnInfo(name = "date_e") val date_e: String?,
    @ColumnInfo(name = "time_e") val time_e: String?,
    @ColumnInfo(name = "city_e") val city_e: String?,
    @ColumnInfo(name = "theme_e") val theme_e: String?,
)