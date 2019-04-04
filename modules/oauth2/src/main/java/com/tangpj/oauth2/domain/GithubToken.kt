package com.tangpj.oauth2.domain

import android.os.Parcelable
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import com.tangpj.oauth2.domain.GithubToken.Companion.TABLE_NAME
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(primaryKeys = ["id"],
        tableName = TABLE_NAME)
data class GithubToken(
        var id: Long = 0,
        @SerializedName("access_token") var accessToken: String,
        @SerializedName("token_type") var tokenType: String?,
        @SerializedName("scope") var scope: String?
        ): Parcelable{
        companion object {
                const val TABLE_NAME = "github_token"
                const val COLUMN_ID = "id"
        }

}

