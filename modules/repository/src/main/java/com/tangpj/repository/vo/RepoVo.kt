package com.tangpj.repository.vo

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import kotlinx.android.parcel.Parcelize

@Parcelize

@Entity(
        indices = [
            Index("id")],
        primaryKeys = ["name"]
)
data class RepoVo constructor(
        val id: String,
        val name: String,
        val fullName: String,
        val language: String,
        val languageColor: String,
        val description: String,
        val stars: Int,
        val forks: Int
): Parcelable{
    override fun toString(): String {
        return name
    }
}




