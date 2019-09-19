package com.tangpj.repository.valueObject.result

import androidx.room.Entity
import androidx.room.TypeConverters
import com.tangpj.github.db.StringListTypeConverters

@Entity(primaryKeys = ["owner","repoName","expression"])
@TypeConverters(StringListTypeConverters::class)
class FileItemsResult(
        val owner: String,
        val repoName: String,
        val expression: String,
        val itemIds: List<String>)