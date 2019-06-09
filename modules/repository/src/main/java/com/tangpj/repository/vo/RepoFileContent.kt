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
data class RepoFileContent(
        val id: String,
        val owner: String,
        val repoName: String,
        val expression: String,
        val content: String,
        val byteSize: Int) : Parcelable