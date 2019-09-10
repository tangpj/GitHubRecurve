package com.tangpj.repository.entity.domain.file

import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tangpj.repository.R
import com.tangpj.repository.entity.BaseEntity
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class FileItem(
        @field:PrimaryKey
        override val id: String,
        val name: String,
        val type: String) : BaseEntity(id){

    @DrawableRes fun getFileIconId() : Int =
            when(type){
                FileType.BLOB -> R.mipmap.ic_description
                FileType.TREE -> R.drawable.ic_folder
                else -> R.mipmap.ic_description
            }
}

