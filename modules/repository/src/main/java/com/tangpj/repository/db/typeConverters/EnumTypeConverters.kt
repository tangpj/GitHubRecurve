package com.tangpj.repository.db.typeConverters

import androidx.room.TypeConverter
import com.tangpj.repository.vo.FileContent

object FileContentTypeConverter{

    @TypeConverter
    fun typeToInt(type: FileContent.Type) = type.ordinal

    @TypeConverter
    fun intToType(value: Int) :  FileContent.Type = FileContent.Type.values()[value]


}