package com.tangpj.repository.valueObject.result

import androidx.room.Entity


@Entity(primaryKeys = ["owner", "repoName", "expression"])
class FileContentResult(
        val owner: String,
        val repoName: String,
        val expression: String,
        val fileContentId: String)