package com.events.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "savedEvent")
data class SavedEvent(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "id_e") val id_e: Int,
    @ColumnInfo(name = "id_user") val id_user: Int,
    @ColumnInfo(name = "name_e") val name_e: String,
    @ColumnInfo(name = "image_e") val image_e: String,
    @ColumnInfo(name = "date_e") val date_e: String,
    @ColumnInfo(name = "time_e") val time_e: String,
    @ColumnInfo(name = "city_e") val city_e: String,
    @ColumnInfo(name = "theme_e") val theme_e: String,
)