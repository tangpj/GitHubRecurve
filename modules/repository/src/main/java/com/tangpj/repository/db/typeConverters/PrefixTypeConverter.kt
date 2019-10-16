package com.tangpj.repository.db.typeConverters

import androidx.room.TypeConverter
import com.tangpj.repository.valueObject.query.Prefix

object PrefixTypeConverter{

    @TypeConverter
    @JvmStatic
    fun prefixToString(prefix: Prefix) = prefix.refName

    @TypeConverter
    @JvmStatic
    fun stringToPrefix(refName: String) = when(refName){
        Prefix.HEAD.refName -> Prefix.HEAD
        Prefix.TAGS.refName -> Prefix.TAGS
        else -> throw IllegalArgumentException(
                "refName cannot converter to Prefix , refName = $refName")
    }
}