package com.tangpj.repository.entry.vo

import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tangpj.repository.R
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
class FileItem(
        @field:PrimaryKey
        override val id: String,
        val name: String,
        val type: String) : Entry(id){

    @DrawableRes fun getFileIconId() : Int =
            when(type){
                FileType.BLOB -> R.mipmap.ic_description
                FileType.TREE -> R.drawable.ic_folder
                else -> R.mipmap.ic_description
            }
}

