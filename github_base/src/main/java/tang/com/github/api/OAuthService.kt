package tang.com.github.api

import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface OAuthService {
    @POST("login/oauth/access_token")
    @Headers("Accept: application/json")
    fun getToken(@Body var1: RequestToken): Single<Response<GitHubToken>>
}