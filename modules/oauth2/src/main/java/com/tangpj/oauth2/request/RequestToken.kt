package com.tangpj.oauth2.request

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.tangpj.oauth2.BuildConfig
import com.tangpj.oauth2.GithubOauth2
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RequestToken(
        @SerializedName ("client_id") val clientId: String = BuildConfig.CLIENT_ID,
        @SerializedName("client_secret") val client_secret: String = BuildConfig.CLIENT_SECRET,
        @SerializedName("redirect_uri") var redirectUri: String? = null,
        @SerializedName("code") var code: String): Parcelable