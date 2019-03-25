package com.tangpj.github.core.apollo

import com.apollographql.android.converter.pojo.CustomTypeAdapter
import java.text.DateFormat
import java.util.*
import javax.inject.Inject

class DateCustomerAdapter @Inject constructor() : CustomTypeAdapter<Date>{

    override fun encode(value: Date?): String =
        value.toString()


    override fun decode(value: String?): Date = DateFormat.getInstance().parse(value)

}