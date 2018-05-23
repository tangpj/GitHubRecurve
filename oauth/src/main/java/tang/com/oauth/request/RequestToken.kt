package tang.com.oauth.request

import com.google.gson.annotations.SerializedName

data class RequestToken constructor(
        @SerializedName ("client_id") val clientId: String,
        @SerializedName("client_secret") val client_secret: String,
        @SerializedName("redirect_uri") val redirectUri: String)