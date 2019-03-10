package com.tangpj.repository.vo

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import com.tangpj.repository.domain.OwnerDo
import kotlinx.android.parcel.Parcelize

@Parcelize

@Entity(
        indices = [
            Index("id")],
        primaryKeys = ["name"]
)
data class RepoVo @JvmOverloads constructor(
        var id: Int,
        var name: String,
        var fullName: String? = null,
        var language: String? = null,
        var languageColor: String? = null,
        var description: String? = null,
        var stars: Int = 0,
        var forks: Int = 0
): Parcelable{
    constructor() : this(0,"", null)
}