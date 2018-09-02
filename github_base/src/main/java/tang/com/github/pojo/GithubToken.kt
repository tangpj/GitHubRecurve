package tang.com.github.pojo

import android.os.Parcelable
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(primaryKeys = ["accessToken"])
data class GithubToken(
        @SerializedName("access_token") val accessToken: String,
        @SerializedName("token_type") val tokenType: String?,
        @SerializedName("scope") val scope: String?): Parcelable
