package com.tangpj.repository.vo

import androidx.annotation.IdRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tangpj.repository.R

@Entity
class FileItem(
        @field:PrimaryKey
        val id: String,
        val name: String,
        val type: String){

    fun isTree() = type == "tree"

    @IdRes fun getFileIconId() : Int =
            when(type){
                "blob" -> R.drawable.ic_folder
                "tree" -> R.mipmap.ic_description
                else -> R.mipmap.ic_description
            }
}