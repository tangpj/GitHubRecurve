/*
 * Copyright (C) 2018 The Tang
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

package tang.com.mvvmrecurve

import android.support.v4.util.ArrayMap
import retrofit2.Response
import tang.com.recurve.resource.ApiResponse
import timber.log.Timber
import java.util.regex.Pattern

/**
 * Created by tang on 2018/2/28.
 */
class GitHubApiResponse<T> : ApiResponse<T> {

    private val links: Map<String, String>

    constructor(error: Throwable) : super(error){
        links = emptyMap()
    }

    constructor(response: Response<T>) : super(response){
        val linkHeader = response.headers().get("link")
        if (linkHeader == null) {
            links = emptyMap()
        } else {
            links = ArrayMap()
            val matcher = LINK_PATTERN.matcher(linkHeader)

            while (matcher.find()) {
                val count = matcher.groupCount()
                if (count == 2) {
                    links.put(matcher.group(2), matcher.group(1))
                }
            }
        }
    }

    override fun nextPageRule(): Int? {
        val next = links[NEXT_LINK] ?: return null
        val matcher = PAGE_PATTERN.matcher(next)
        if (!matcher.find() || matcher.groupCount() != 1) {
            return null
        }
        return try {
            Integer.parseInt(matcher.group(1))
        } catch (ex: NumberFormatException) {
            Timber.w("cannot parse next page from %s", next)
            null
        }
    }

    companion object {
        private val LINK_PATTERN = Pattern
                .compile("<([^>]*)>[\\s]*;[\\s]*rel=\"([a-zA-Z0-9]+)\"")
        private val PAGE_PATTERN = Pattern.compile("\\bpage=(\\d+)")
        private const val NEXT_LINK = "next"
    }

}