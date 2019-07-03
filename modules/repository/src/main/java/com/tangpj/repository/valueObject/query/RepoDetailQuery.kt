package com.tangpj.repository.valueObject.query

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RepoDetailQuery(val owner: String, val name: String) : Parcelable