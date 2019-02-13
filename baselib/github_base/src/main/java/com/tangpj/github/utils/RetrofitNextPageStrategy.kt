package com.tangpj.github.utils

import com.tangpj.recurve.resource.NextPageStrategy
import retrofit2.Response
import timber.log.Timber
import java.util.regex.Pattern

class RetrofitNextPageStrategy : NextPageStrategy<Response<*>>{

    companion object {
        private val LINK_PATTERN = Pattern.compile("<([^>]*)>[\\s]*;[\\s]*rel=\"([a-zA-Z0-9]+)\"")
        private val PAGE_PATTERN = Pattern.compile("\\bpage=(\\d+)")
        private const val NEXT_LINK = "next"

        private lateinit var links: Map<String,String>

        private fun String.extractLinks(): Map<String, String> {
            val links = mutableMapOf<String, String>()
            val matcher = LINK_PATTERN.matcher(this)

            while (matcher.find()) {
                val count = matcher.groupCount()
                if (count == 2) {
                    links[matcher.group(2)] = matcher.group(1)
                }
            }
            return links
        }

    }

    override fun setResponse(response: Response<*>) {
        links = response.headers()?.get("link")?.extractLinks() ?: mapOf()
    }

    override fun nextPageRule(): Int{
        return links[NEXT_LINK]?.let { next ->
            val matcher = PAGE_PATTERN.matcher(next)
            if (!matcher.find() || matcher.groupCount() != 1) {
                null
            } else {
                try {
                    Integer.parseInt(matcher.group(1))
                } catch (ex: NumberFormatException) {
                    Timber.w("cannot parse next page from %s", next)
                    null
                }
            }
        } ?: -1
    }

}
