package com.tangpj.github.core.apollo

import com.apollographql.apollo.response.CustomTypeAdapter
import com.apollographql.apollo.response.CustomTypeValue
import java.text.DateFormat
import java.util.*
import javax.inject.Inject

class DateCustomerAdapter @Inject constructor() : CustomTypeAdapter<Date> {
    override fun encode(value: Date): CustomTypeValue<*>  =
            CustomTypeValue.GraphQLString(value.toString())

    override fun decode(value: CustomTypeValue<*>): Date =
            DateFormat.getInstance().parse(value.value.toString())
}