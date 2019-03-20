package com.tangpj.repository.domain

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Index
import com.tangpj.github.domain.RepoFlag
import com.tangpj.github.domain.RepoFlag.QUERY
import com.tangpj.repository.type.CustomType
import kotlinx.android.parcel.Parcelize
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

@Parcelize
@Entity(primaryKeys = ["login","repoId"],
        indices = [
                Index("login"),
                Index("repoId")])
class UserRepoResult @JvmOverloads @Ignore constructor(
        var login: String,
        var repoId: String,
        var type: Int = QUERY): Parcelable{

        constructor() : this("","")
}

