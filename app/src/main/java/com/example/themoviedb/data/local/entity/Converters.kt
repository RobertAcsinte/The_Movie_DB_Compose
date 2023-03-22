package com.example.themoviedb.data.local.entity

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.themoviedb.util.JsonParser
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


@ProvidedTypeConverter
class Converters(
    private val jsonParser: JsonParser
){
    @TypeConverter
    fun toIntListJson(list: List<Int>): String {
        return jsonParser.toJson(
            list,
            object : TypeToken<List<Int>>(){}.type
        ) ?: "[]"
    }

    @TypeConverter
    fun fromIntListJson(json: String): List<Int> {
        return jsonParser.fromJson<List<Int>>(
            json,
            object: TypeToken<List<Int>>(){}.type
        ) ?: emptyList()
    }

    @TypeConverter
    fun toStringListJson(list: List<String>?): String {
        return jsonParser.toJson(
            list,
            object : TypeToken<List<String>>(){}.type
        ) ?: "[]"
    }

    @TypeConverter
    fun fromStringListJson(json: String): List<String> {
        return jsonParser.fromJson<List<String>>(
            json,
            object: TypeToken<List<String>>(){}.type
        ) ?: emptyList()
    }
}