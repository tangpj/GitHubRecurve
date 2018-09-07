package com.tangpj.github.utils

import retrofit2.Response
import com.tangpj.github.GithubApp
import java.net.HttpURLConnection

fun <T> throwOnFailure(response: Response<T>): T? {
    if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {

    }
    if (!response.isSuccessful) {

    }
    return response.body()
}