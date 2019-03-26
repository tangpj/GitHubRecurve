package com.tangpj.repository.domain

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Index
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(primaryKeys = ["login","repoId"],
        indices = [
                Index("login"),
                Index("repoId")])
class StarRepoResult @JvmOverloads @Ignore constructor(
        var login: String,
        var repoId: String,
        var starredAt: Long =0): Parcelable{
        constructor() : this("","")
}

