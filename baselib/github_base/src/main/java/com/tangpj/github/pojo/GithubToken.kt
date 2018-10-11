package com.tangpj.github.pojo

import android.os.Parcelable
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(primaryKeys = ["id"])
data class GithubToken(
        var id: Long = 0,
        @SerializedName("access_token") var accessToken: String,
        @SerializedName("token_type") var tokenType: String?,
        @SerializedName("scope") var scope: String?
        ): Parcelable
