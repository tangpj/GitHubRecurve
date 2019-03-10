package com.tangpj.repository.domain

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Index
import com.tangpj.github.domain.RepoFlag
import com.tangpj.github.domain.RepoFlag.QUERY
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(primaryKeys = ["login","repoId"],
        indices = [
                Index("login"),
                Index("repoId")])
class UserRepoResult @JvmOverloads @Ignore constructor(
        var login: String,
        var repoId: String,
        @RepoFlag
        var type: Int = QUERY): Parcelable{

        constructor() : this("","")
}

