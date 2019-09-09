package com.tangpj.repository.db.typeConverters

import androidx.room.TypeConverter
import com.tangpj.repository.entity.file.FileContent

object FileContentTypeConverter{

    @TypeConverter
    @JvmStatic
    fun typeToInt(type: FileContent.Type) = type.ordinal

    @TypeConverter
    @JvmStatic
    fun intToType(value: Int) :  FileContent.Type = FileContent.Type.values()[value]

}