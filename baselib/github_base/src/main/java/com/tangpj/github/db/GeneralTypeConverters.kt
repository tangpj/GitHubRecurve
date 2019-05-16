package com.tangpj.github.db

import androidx.room.TypeConverter
import timber.log.Timber
import java.lang.NumberFormatException

object IntListTypeConverters{

    @TypeConverter
    @JvmStatic
    fun stringToIntList(data: String?) : List<Int>? =
            data?.let {
                it.split(",").map { s ->
                    try {
                        s.toInt()
                    }catch (ex: NumberFormatException){
                        Timber.e(ex, "Cannot convert $s to number")
                        null
                    }
                }
            }?.filterNotNull()

    @TypeConverter
    @JvmStatic
    fun intListToString(ints: List<Int>?): String? {
        return ints?.joinToString(",")
    }

}


object StringListTypeConverters{

    @TypeConverter
    @JvmStatic
    fun stringToStringList(data: String?) : List<String>? =
            data?.let {
                it.split(",")
            }

    @TypeConverter
    @JvmStatic
    fun stringListToString(ints: List<String>?): String? {
        return ints?.joinToString(",")
    }

}