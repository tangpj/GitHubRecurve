package com.tangpj.repository.entity.domain.file

import androidx.room.Entity
import androidx.room.Index
import androidx.room.TypeConverters
import com.tangpj.repository.db.typeConverters.FileContentTypeConverter
import com.tangpj.github.entity.BaseEntity
import kotlinx.android.parcel.Parcelize




/**
 *
 * 文件内容
 *
 * @className: RepoFileContent
 * @author create by Tang
 * @date  23:15
 */
@Entity(
        primaryKeys = ["id"],
        indices = [Index("id")]
)
@TypeConverters(FileContentTypeConverter::class)
@Parcelize
class FileContent(
        override val id: String,
        val content: String,
        val type: Type,
        val byteSize: Int) : BaseEntity(id){

    enum class Type{
        DEFAULT,
        MARK_DOWN,
        CODE
    }
}

