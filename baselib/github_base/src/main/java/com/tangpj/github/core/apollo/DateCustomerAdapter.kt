package com.tangpj.github.core.apollo

import com.apollographql.apollo.response.CustomTypeAdapter
import com.apollographql.apollo.response.CustomTypeValue
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import javax.inject.Inject

class DateCustomerAdapter @Inject constructor() : CustomTypeAdapter<LocalDateTime> {
    override fun encode(value: LocalDateTime): CustomTypeValue<*>  =
            CustomTypeValue.GraphQLString(value.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))

    override fun decode(value: CustomTypeValue<*>): LocalDateTime =
            LocalDateTime.parse(value.value.toString(), DateTimeFormatter.ISO_OFFSET_DATE_TIME)
}