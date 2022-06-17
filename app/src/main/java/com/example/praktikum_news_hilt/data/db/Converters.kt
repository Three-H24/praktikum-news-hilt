package com.example.praktikum_news_hilt.data.db

import androidx.room.TypeConverter
import com.example.praktikum_news_hilt.data.model.Source

class Converters {

    @TypeConverter
    fun fromSource(source: Source): String {
        return source.name.toString()
    }

    @TypeConverter
    fun toSource(name: String): Source {
        return Source(name, name)
    }
}