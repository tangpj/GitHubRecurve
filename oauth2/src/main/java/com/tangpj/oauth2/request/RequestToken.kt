package com.tangpj.oauth2.request

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RequestToken(
        @SerializedName ("client_id") val clientId: String,
        @SerializedName("client_secret") val client_secret: String,
        @SerializedName("redirect_uri") var redirectUri: String? = null,
        @SerializedName("code") val code: String): Parcelable