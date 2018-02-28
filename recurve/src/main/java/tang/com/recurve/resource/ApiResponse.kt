/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tang.com.recurve.resource

import android.support.v4.util.ArrayMap
import retrofit2.Response
import timber.log.Timber
import java.io.IOException
import java.util.regex.Pattern

/**
 * Created by tang on 2018/2/28.
 * Common class used by API responses.
 * @param <T>
</T> */
abstract class ApiResponse<T> {
    val code: Int
    val body: T?
    val errorMessage: String?

    val isSuccessful: Boolean
        get() = code in 200..299

    val nextPage: Int?
        get() = nextPageRule()

    constructor(error: Throwable) {
        code = 500
        body = null
        errorMessage = error.message
    }

    constructor(response: Response<T>) {
        code = response.code()
        if (response.isSuccessful) {
            body = response.body()
            errorMessage = null
        } else {
            var message: String? = null
            if (response.errorBody() != null) {
                try {
                    message = response.errorBody()!!.string()
                } catch (ignored: IOException) {
                    Timber.e(ignored, "error while parsing response")
                }

            }
            if (message == null || message.trim { it <= ' ' }.isEmpty()) {
                message = response.message()
            }
            errorMessage = message
            body = null
        }

    }

    protected abstract fun nextPageRule() : Int?

}
