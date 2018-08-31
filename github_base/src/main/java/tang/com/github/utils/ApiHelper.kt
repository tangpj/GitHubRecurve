package tang.com.github.utils

import retrofit2.Response
import tang.com.github.GitHubApp
import java.net.HttpURLConnection

fun <T> throwOnFailure(response: Response<T>): T? {
    if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
        GitHubApp.get().logout()
    }
    if (!response.isSuccessful) {

    }
    return response.body()
}