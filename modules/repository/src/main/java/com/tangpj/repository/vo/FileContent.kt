package com.tangpj.repository.vo

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import kotlinx.android.parcel.Parcelize




/**
 * 资源库的某个文件
 *
 * @className: RepoFileContent
 * @author create by Tang
 * @date  23:15
 */
@Entity(
        primaryKeys = ["id"],
        indices = [Index("id")]
)
@Parcelize
data class FileContent(
        val id: String,
        val content: String,
        val type: Type,
        val byteSize: Int) : Parcelable{

    enum class Type{
        MARK_DOWN
    }
}

