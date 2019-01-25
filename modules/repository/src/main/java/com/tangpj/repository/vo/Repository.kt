package com.tangpj.repository.vo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * 仓库栏目
 *
 * @className: Repository
 * @author: tangpengjian113
 * @createTime: 2019/1/18 14:53
 */

@Parcelize
data class Repository(
        var title: String,
        var desc: String = "",
        var language: String = "kotlin",
        var star: Int = 2000,
        var forked: Int = 2000): Parcelable