package com.tangpj.github.customtype

import com.apollographql.apollo.response.CustomTypeAdapter
import com.apollographql.apollo.response.CustomTypeValue
import javax.inject.Inject

class UriCustomTypeAdapter @Inject constructor() : CustomTypeAdapter<String>{

    override fun encode(value: String): CustomTypeValue<*> =
        CustomTypeValue.GraphQLString(value)



    override fun decode(value: CustomTypeValue<*>): String = value.value.toString()

}