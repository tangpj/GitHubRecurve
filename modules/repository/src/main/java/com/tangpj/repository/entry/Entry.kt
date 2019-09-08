package com.tangpj.repository.entry

import android.os.Parcelable
import androidx.room.Ignore
import kotlinx.android.parcel.Parcelize

/**
 * entity class base class, all entities need to extends this class
 * [id] Do not make the database primary key,
 * the primary key should be determined by the actual business (eg: login + repo name)
 *
 * @className: Entry
 * @author create by Tang
 * @date  17:39
 */

@Parcelize
open class Entry(
        @Ignore
        open val id: String) : Parcelable