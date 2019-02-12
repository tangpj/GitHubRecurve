package com.tangpj.github.utils

import com.apollographql.apollo.api.Response
import com.tangpj.recurve.resource.NextPageStrategy

class ApolloNextPageStrategy() : NextPageStrategy<Response<*>> {
    override fun setResponse(response: Response<*>) {
    }

    override fun nextPageRule(): Int {
        return -1
    }

}